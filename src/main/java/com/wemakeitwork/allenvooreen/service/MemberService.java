package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.PasswordResetToken;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.PasswordResetTokenRepository;
import com.wemakeitwork.allenvooreen.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByMemberName(String memberName) {
        return memberRepository.findByMemberName(memberName);
    }

    public Member findMemberByEmail(final String email) {
        return memberRepository.findByEmail(email);
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

    @Override
    public void createPasswordResetTokenForMember(Member member, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, member);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public Member getMemberByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token)
                .getMember();
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }


    @Override
    public Optional<Member> getMemberByID(int id) {
        return memberRepository.findById(id);
    }

    @Override
    public void changeMemberPassword(Member member, String password) {
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    @Override
    public boolean checkIfValidOldPassword(Member member, String oldPassword) {
        return passwordEncoder.matches(oldPassword, member.getPassword());
    }

}
