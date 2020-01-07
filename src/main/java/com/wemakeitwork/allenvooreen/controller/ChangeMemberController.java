package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ChangeMemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("member/current")
    protected String showMember(Model model, Principal principal){
        model.addAttribute("currentmember", memberRepository.findByMembername(principal.getName()));
        return "memberOverview";
    }

    @PostMapping("/member/change")
    protected String saveOrUpdateMember(@ModelAttribute("member") Member newNameMember, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "changeMember";
        } else {
            Optional<Member> originalMember = memberRepository.findByMembername(principal.getName());
            if (originalMember.isPresent()){
                newNameMember.setPassword(originalMember.get().getPassword());
                newNameMember.setMemberId(originalMember.get().getMemberId());
            }
            memberRepository.save(newNameMember);
            return "redirect:/member/current";
        }
    }

    @GetMapping("/member/change")
    protected String goToMemberChange(Model model){
        model.addAttribute("member", new Member());
        return "changeMember";
    }
}











