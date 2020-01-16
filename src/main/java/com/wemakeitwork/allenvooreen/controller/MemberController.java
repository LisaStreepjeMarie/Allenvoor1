package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.service.MemberService;
import com.wemakeitwork.allenvooreen.service.SecurityService;
import com.wemakeitwork.allenvooreen.validator.MemberValidator;
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
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MemberValidator memberValidator;

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

    // voorbeeldcode nog toepassen?
    /* @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    } */


    @GetMapping("/member/delete")
    public String deleteMember(Principal principal) {
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        member.ifPresent(value -> memberRepository.delete(value));
        return "confirmLogout";
    }

    //@PostMapping("/member/new")
    /* protected String saveOrUpdateMember(@ModelAttribute("member") @Valid Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "newMember";
        }
        else {
            //TODO: check of aan te maken loginnaam al bestaat (gooit nu whitelabel 500 met SQL constraint error)
            System.out.println(member.getMemberName());
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRol("gebruiker");
            memberRepository.save(member);
            return "redirect:/member/new";
        }
    } */
    @PostMapping("/member/new")
    public String saveOrUpdateMember(@ModelAttribute("member") Member member, BindingResult result) {
        memberValidator.validate(member, result);
        if (result.hasErrors()) {
            return "newMember";
        } else {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRol("gebruiker");
            // memberRepository.save(member);
            memberService.save(member);
            // securityService.autoLogin(member.getUsername(), member.getPasswordConfirm());

            return "redirect:/member/new";
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




