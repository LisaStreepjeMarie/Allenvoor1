package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Chat;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Message;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.MessageRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/chat")
    protected String showEventForm() {
        System.out.println("hallo");
        return "chat";
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

}
