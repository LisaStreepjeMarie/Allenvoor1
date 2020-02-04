package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberServiceInterface;
import com.wemakeitwork.allenvooreen.service.SecurityServiceInterface;
import com.wemakeitwork.allenvooreen.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberServiceInterface memberServiceInterface;

    @Autowired
    private SecurityServiceInterface securityServiceInterface;

    @Autowired
    private MemberValidator memberValidator;

    @GetMapping("member/current")
    protected String showMember(Model model){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentmember", member);
        return "memberProfile";
    }

    @GetMapping("/member/change")
    protected String changeMember(Model model){
        model.addAttribute("member", new Member());
        return "changeMember";
    }

    @GetMapping("/member/select/{memberId}")
    protected String showMemberData(Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("member", member);
        return "changeMember";
    }

    @GetMapping("/member/new")
    //@Secured("ROLE_ADMIN")
    protected String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "newMember";
    }

    @GetMapping("/member/delete")
    public String deleteMember() {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memberRepository.delete(member);
        return "confirmLogout";
    }

    @PostMapping("/member/new")
    protected String newMember(@ModelAttribute("member") @Valid Member member, BindingResult result) {
        System.out.println("is er output? " + member.getMemberName());
        memberValidator.validate(member, result);
        if (result.hasErrors()) {
            return "newMember";
        } else {
            System.out.println(member.getMemberName());
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberServiceInterface.save(member);
            securityServiceInterface.autoLogin(member.getUsername(), member.getPasswordConfirm());
            return "redirect:/login";
        }
    }

    @PostMapping("/member/change")
    protected String changeMember(@ModelAttribute("currentmember") Member newNameMember, BindingResult result) {
        if (result.hasErrors()) {
            return "currentMember";
        } else {
            Member originalMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            newNameMember.setPassword(originalMember.getPassword());
            newNameMember.setMemberId(originalMember.getMemberId());
            memberRepository.save(newNameMember);
            return "redirect:/logout";
        }
    }
}




