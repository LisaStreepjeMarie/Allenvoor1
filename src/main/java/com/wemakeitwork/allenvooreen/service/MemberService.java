package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService implements MemberServiceInterface {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByMembername(String membername) {
        return memberRepository.findByMemberName(membername);
    }

    @Override
    public void createVerificationToken(Member member, String token) {
        VerificationToken newUserToken = new VerificationToken(token, member);
    }

    @Override
    public void enableRegisteredUser(Member member) {
        memberRepository.save(member);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }
}
