package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeleteMemberController {

    @Autowired
    MemberRepository memberRepository;

    // Verwijder gebruiker (ingelogd gebruiker kan zichzelf verwijderen)
    @GetMapping("/member/delete/{memberId}")
    public String deleteMember(@PathVariable("memberId") final Integer memberId) {
        memberRepository.deleteById(memberId);
        return "redirect:/member/all";
    }
}




