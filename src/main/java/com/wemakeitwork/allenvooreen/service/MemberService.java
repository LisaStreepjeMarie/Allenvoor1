package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService implements MemberServiceInterface {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenRepository tokenRepository;


    /*@Override
    @Transactional
    public Member registerMember(Member member) {
        member = new Member();
        member.setMemberName(member.getMemberName());
        String hashedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPassword);
        member.setEnabled(member.isEnabled());
        member.setEmail(member.getEmail());

        member.setRol(member.getRol());
        memberRepository.save(member);
        return member;
    }


     */

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByMemberName(String memberName) {
        return memberRepository.findByMemberName(memberName);
    }

    @Override
    public void createVerificationToken(Member member, String token) {
        Optional<Member> optionalMember = memberRepository.findByMemberName(member.getMemberName());
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
        }
        VerificationToken newUserToken = new VerificationToken(token, member);
        tokenRepository.save(newUserToken);
    }


    public void enableRegisteredUser(Member member) {
        memberRepository.save(member);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }
}
