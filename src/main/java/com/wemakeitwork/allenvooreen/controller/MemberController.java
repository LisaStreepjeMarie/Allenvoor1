package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.MemberServiceInterface;
import com.wemakeitwork.allenvooreen.service.SecurityServiceInterface;
import com.wemakeitwork.allenvooreen.validator.MemberValidator;
import com.wemakeitwork.allenvooreen.web.error.MemberAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;


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

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping("member/current")
    protected String showMember(Model model, Principal principal){
        model.addAttribute("currentmember", memberRepository.findByMemberName(principal.getName()).orElse(new Member()));
        return "memberProfile";
    }

    @GetMapping("/member/change")
    protected String changeMember(Model model){
        model.addAttribute("member", new Member());
        return "changeMember";
    }

    @GetMapping("/member/select/{memberId}")
    protected String showMemberData(Model model, Principal principal) {
        model.addAttribute("member", new Member());

        Optional<Member> memberOpt = memberRepository.findByMemberName(principal.getName());
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

    @GetMapping("/member/delete")
    public String deleteMember(Principal principal) {
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        member.ifPresent(value -> memberRepository.delete(value));
        return "confirmLogout";
    }

    @PostMapping("/member/new")
    protected String saveOrUpdateMember(@ModelAttribute("member") @Valid Member member, BindingResult result) {
        System.out.println("is er output? " + member.getMemberName());
        memberValidator.validate(member, result);
        if (result.hasErrors()) {
            return "newMember";
        } else {
            System.out.println(member.getMemberName());
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRol("gebruiker");
            memberServiceInterface.save(member);
            securityServiceInterface.autoLogin(member.getUsername(), member.getPasswordConfirm());
            return "redirect:/login";
        }
    }

    @PostMapping("/member/change")
    protected String saveOrUpdateMember(@ModelAttribute("currentmember") Member newNameMember, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "currentMember";
        } else {
            Optional<Member> originalMember = memberRepository.findByMemberName(principal.getName());
            if (originalMember.isPresent()){
                newNameMember.setPassword(originalMember.get().getPassword());
                newNameMember.setMemberId(originalMember.get().getMemberId());
                newNameMember.setRol(originalMember.get().getRol());
            }
            memberRepository.save(newNameMember);
            return "redirect:/logout";
        }
    }
}








