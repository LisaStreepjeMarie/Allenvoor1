package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.repository.event.OnRegistrationSuccessEvent;
import com.wemakeitwork.allenvooreen.service.MemberServiceInterface;
import com.wemakeitwork.allenvooreen.service.SecurityServiceInterface;
import com.wemakeitwork.allenvooreen.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Logger;


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
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    private Logger logger = Logger.getLogger(getClass().getName());

    private Object Locale;

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
    protected String newMember(@ModelAttribute("member") @Valid Member member, BindingResult result, WebRequest request, Model model) {
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
            try {
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationSuccessEvent(member, appUrl));
            }catch(Exception re) {
                re.printStackTrace();
            }
            return "redirect:/signup-success";
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
            newNameMember.setRol(originalMember.getRol());
            memberRepository.save(newNameMember);
            return "redirect:/logout";
        }
    }

    @GetMapping("/confirmRegistration")
    public String confirmRegistration(WebRequest request, Model model,@RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken =  memberServiceInterface.getVerificationToken(token);
        if(verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:access-denied";
        }
        Member member = verificationToken.getMember();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "redirect:access-denied";
        }
        member.setEnabled(true);
        memberServiceInterface.enableRegisteredUser(member);
        return "confirmRegistration";
    }
}






