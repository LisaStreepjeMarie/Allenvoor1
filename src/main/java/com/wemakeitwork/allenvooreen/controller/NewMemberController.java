package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Optional;


@Controller
public class NewMemberController{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("member/current")
    protected String showMember(Model model, Principal principal){
        model.addAttribute("currentmember", memberRepository.findByMembername(principal.getName()));
        return "memberOverview";
    }

    @GetMapping("/member/select/{memberId}")
    protected String showMemberData(@PathVariable("memberId") final Integer memberId, Model model, Principal principal) {
        // extra regel om te testen:
        model.addAttribute("member", new Member());
        Optional<Member> memberOpt = memberRepository.findByMembername(principal.getName());
        Member member;
        if (memberOpt.isPresent()) {
            member = memberOpt.get();
        } else {
            member = new Member();
        }
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




