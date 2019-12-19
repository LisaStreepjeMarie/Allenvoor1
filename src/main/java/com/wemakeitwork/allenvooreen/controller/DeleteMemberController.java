package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeleteMemberController {

    @Autowired
    MemberRepository memberRepository;

    // Verwijder gebruiker (ingelogd gebruiker kan zichzelf verwijderen)
    @RequestMapping("/member/delete/member")
    @ResponseBody
    public String deleteMember(int memberId) {
        try {
            memberRepository.delete(memberRepository.getOne(memberId));
        } catch (Exception ex) {
            return "Error bij het verwijderen van gebruiker:" + ex.toString();
        }
        return "Gebruiker verwijderd!";
    }
}




