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
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("member/current")
    protected String showMember(Model model, Principal principal){
        model.addAttribute("currentmember", memberRepository.findByMembername(principal.getName()).orElse(new Member()));
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

    @GetMapping("/member/delete")
    public String deleteMember(Principal principal) {
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        member.ifPresent(value -> memberRepository.delete(value));
        return "/logout";
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

    @PostMapping("/member/change")
    protected String saveOrUpdateMember(@ModelAttribute("currentmember") Member newNameMember, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "currentMember";
        } else {
            Optional<Member> originalMember = memberRepository.findByMembername(principal.getName());
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




