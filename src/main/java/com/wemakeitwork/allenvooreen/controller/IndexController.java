package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    ActivityRepository activityRepository;

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
    public String calendar(Model model) {
        model.addAttribute("activityList", activityRepository.findAll());;
        return "calendar2";
    }

}