package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.Optional;

@Controller
public class DeleteMemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/member/delete")
    public String deleteMember(Principal principal) {
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        member.ifPresent(value -> memberRepository.delete(value));
        return "/logout";
    }
}




