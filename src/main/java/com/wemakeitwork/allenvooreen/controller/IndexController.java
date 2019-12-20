package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wemakeitwork.allenvooreen.model.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }

    @GetMapping("/calendar")
    public String calendar(@ModelAttribute("event") Event event, Model model) throws JsonProcessingException {
        String eventJson = EventToJson.eventToJson(event);
        model.addAttribute(eventJson);
        return "calendar";
    }

}