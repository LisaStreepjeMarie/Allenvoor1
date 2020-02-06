package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface MemberServiceInterface {

    /*public Member registerMember(Member member);

     */

    void save(Member member);

    Optional<Member> findByMemberName(String memberName);

    public void createVerificationToken(Member member, String token);

    public void enableRegisteredUser(Member member);

    public VerificationToken getVerificationToken(String verificationToken);
}

