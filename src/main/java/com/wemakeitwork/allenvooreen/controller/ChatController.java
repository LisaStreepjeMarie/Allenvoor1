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

    @GetMapping("/chat")
    protected String showEventForm(Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<TeamMembership> teamMembershipList = memberRepository.findByMemberName(member.getMemberName()).get().getTeamMemberships();
        ArrayList<Team> teamList = new ArrayList<>();
        for (TeamMembership tms: teamMembershipList) {
            teamList.add(tms.getTeam());
        }

        ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                .sorted(Comparator.comparing(Team::getTeamName))
                .collect(Collectors.toList());

        model.addAttribute("teamList", sortedList);
        return "chatWebPage";
    }

    //TODO all the team stuff is set to one, needs to be done sessionwise or pathvariable

    @PostMapping("/add/message")
    public ResponseEntity<Object> addMessage(@RequestBody String newMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(newMessage, Message.class);

        LocalDateTime dateNow = LocalDateTime.now();
        message.setDateTime(dateNow);

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setMember(member);

        message.setChat(teamRepository.findById(1).get().getChat());
        messageRepository.save(message);

        ServiceResponse<Message> response = new ServiceResponse<Message>("success", message);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/chat/getAll")
    public ResponseEntity<Object> getAllMessages() {


        Team team = teamRepository.getOne(1);
        Chat chat = team.getChat();

        ServiceResponse<Chat> response = new ServiceResponse<>("succes", chat);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/chat/checkNewMessages/{sizeList}")
    public ResponseEntity<Object> checkNewMessages(@PathVariable("sizeList") Integer sizeList){
        Chat chat = teamRepository.getOne(1).getChat();

        if (chat.getMessages().size() == sizeList){
            return new ResponseEntity<>("nothingNew", HttpStatus.OK);
        } else {
            List<Message> newMessages = new ArrayList<>();

            int amountNewMessages = chat.getMessages().size() - sizeList;

            for (int i = 0; i < amountNewMessages; i++){
                newMessages.add(chat.getMessages().get(sizeList));
                sizeList++;
            }

            ServiceResponse<List<Message>> response = new ServiceResponse<>("newMessages", newMessages);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
