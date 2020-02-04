package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface MemberServiceInterface {

    void save(Member member);

    Optional<Member> findByMembername(String membername);

    public void createVerificationToken(Member member, String token);

    public void enableRegisteredUser(Member member);
}


