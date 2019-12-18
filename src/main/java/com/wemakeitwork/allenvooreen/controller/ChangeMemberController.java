package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class ChangeMemberController {

 @Autowired
 private MemberRepository memberRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;

 @RequestMapping("/member/change")
 @ResponseBody
 public String currentMembername(Principal principal){
  return principal.getName();
 }

 // change member (logged in member can change own membername/password)
 @RequestMapping("/member/update")
 @ResponseBody
 public String changeMember(int memberId, String membername, String password) {
  try {
   Member member = memberRepository.getOne(memberId);
   member.setMembername(membername);
   member.setPassword(passwordEncoder.encode(member.getPassword()));
   memberRepository.save(member);
  } catch (Exception ex) {
   return "Error " + ex.toString();
  }
  return "Gebruiker gewijzigd";
 }
}





