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

    @PostMapping("/member/change")
    protected String saveOrUpdateMember(@ModelAttribute("member") Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "changeMember";
        } else {
            memberRepository.save(member);
            return "redirect:/member/all";
        }
    }

    @GetMapping("/member/change")
    protected String goToMemberChange(@ModelAttribute("member") Member member, BindingResult result){
        return "changeMember";
    }
}











