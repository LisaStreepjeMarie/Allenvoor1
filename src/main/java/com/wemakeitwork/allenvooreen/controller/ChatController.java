package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.MessageRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/chat/{teamId}")
    protected String showEventForm(@PathVariable ("teamId") Integer teamId, Model model) {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<TeamMembership> teamMembershipList = memberRepository.findByMemberName(member.getMemberName()).get().getTeamMemberships();

        ArrayList<Team> teamList = new ArrayList<>();
        for (TeamMembership tms: teamMembershipList) {
            teamList.add(tms.getTeam());
        }

        ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                .sorted(Comparator.comparing(Team::getTeamName))
                .collect(Collectors.toList());

        model.addAttribute("member", member);
        model.addAttribute("teamList", sortedList);
        return "chatWebPage";
    }

    @PostMapping("/add/message")
    public ResponseEntity<Object> addMessage(@RequestBody String newMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(newMessage, Message.class);

        LocalDateTime dateNow = LocalDateTime.now();
        message.setDateTime(dateNow);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setMember(member);

        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();

        message.setChat(teamRepository.findById(teamId).get().getChat());
        messageRepository.save(message);

        ServiceResponse<Message> response = new ServiceResponse<Message>("success", message);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/chat/getAll")
    public ResponseEntity<Object> getAllMessages() {
        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();

        Team team = teamRepository.getOne(teamId);
        Chat chat = team.getChat();

        ServiceResponse<Chat> response = new ServiceResponse<>("succes", chat);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/chat/checkNewMessages/{sizeList}")
    public ResponseEntity<Object> checkNewMessages(@PathVariable("sizeList") Integer sizeList){
        Integer teamId = ((Team) httpSession.getAttribute("team")).getTeamId();
        Chat chat = teamRepository.getOne(teamId).getChat();

        // checking if the given list size is the same as the database one from the team
        if (chat.getMessages().size() == sizeList){

            // if it is, nothing needs to be done
            return new ResponseEntity<>("nothingNew", HttpStatus.OK);
        } else {

            // else we need to get the new messages in the array list below
            List<Message> newMessages = new ArrayList<>();

            //checking how many new messages we need
            int amountNewMessages = chat.getMessages().size() - sizeList;
            for (int i = 0; i < amountNewMessages; i++){

                // the given list size is the correct array place for the newest we need, so we can start there
                newMessages.add(chat.getMessages().get(sizeList));

                // adding 1 to the list size so we can get the next one if needed
                sizeList++;
            }

            // making a new service response with the text newMessages so we can check for this on the Ajax side
            ServiceResponse<List<Message>> response = new ServiceResponse<>("newMessages", newMessages);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}