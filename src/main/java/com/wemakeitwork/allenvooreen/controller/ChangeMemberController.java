package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.security.Principal;

@Controller
public class ChangeMemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/member/change")
    protected String saveOrUpdateMember(@ModelAttribute("member") int memberId, String membername, String password, BindingResult result) {
        if (result.hasErrors()) {
            return "changeMember";
        } else {
            Member member = memberRepository.getOne(memberId);
            member.setMembername(membername);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
            return "redirect:/member/all";
        }
    }

    @GetMapping("/member/change")
    protected String goToMemberChange(@ModelAttribute("member") Member member, BindingResult result){
        return "changeMember";
    }
}











