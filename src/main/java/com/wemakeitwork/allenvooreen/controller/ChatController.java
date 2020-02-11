package com.wemakeitwork.allenvooreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/chat")
    protected String showEventForm() {
        System.out.println("hallo");
        return "chat";
    }

}
