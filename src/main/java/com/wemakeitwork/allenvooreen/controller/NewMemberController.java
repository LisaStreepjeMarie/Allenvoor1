package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


@Controller
public class NewMemberController{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/member/select/{memberId}")
    protected String showMemberData(Model model, Principal principal) {
        model.addAttribute("member", new Member());

        Optional<Member> memberOpt = memberRepository.findByMembername(principal.getName());
        Member member;
        member = memberOpt.orElseGet(Member::new);
        model.addAttribute("member", member);
        return "changeMember";
    }

    @GetMapping("/member/new")
    //@Secured("ROLE_ADMIN")
    protected String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "newMember";
    }

    @PostMapping("/member/new")
    protected String saveOrUpdateMember(@ModelAttribute("member") @Valid Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "newMember";
        }
        else {
            //TODO: check of aan te maken loginnaam al bestaat (gooit nu whitelabel 500 met SQL constraint error)
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRol("gebruiker");
            memberRepository.save(member);
            return "redirect:/member/new";
        }
    }
}




