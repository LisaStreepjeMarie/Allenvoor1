package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class NewMemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/member/new")
    protected String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "signUpForm";
    }

    @PostMapping("/member/new")
    protected String saveOrUpdateMember(@ModelAttribute("member") Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "signUpForm";
        }
        else {
            member.setPassword(member.getPassword());
            memberRepository.save(member);
            return "redirect:/member/new";
        }
    }
}




