package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;

import java.util.Optional;

public interface MemberServiceInterface {

    /*public Member registerMember(Member member);

     */

    void save(Member member);

    Optional<Member> findByMemberName(String memberName);

    void createVerificationToken(Member member, String token);

    void enableRegisteredUser(Member member);

    VerificationToken getVerificationToken(String verificationToken);
}

