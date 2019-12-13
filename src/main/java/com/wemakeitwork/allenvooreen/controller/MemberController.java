package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository MemberRepository;

    @GetMapping("/user/new")
    @Secured("ROLE_ADMIN")
    protected String showNewUserForm(Model model) {
        model.addAttribute("member", new Member());
        return "userForm";
    }

    @PostMapping("/user/new")
    protected String saveOrUpdateUser(@ModelAttribute("member") Member member, BindingResult result) {
        if(result.hasErrors()){
            return "userForm";
        } else {
            System.out.println("sdjioasjdioasjdiasjdioasjo");
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            return "redirect:/user/new";
        }
    }
}