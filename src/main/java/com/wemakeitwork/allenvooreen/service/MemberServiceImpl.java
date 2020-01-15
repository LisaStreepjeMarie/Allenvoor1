package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        // member.setRoles(new HashSet<>(roleRepository.findAll()));
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByMembername(String membername) {
        return memberRepository.findByMembername(membername);
    }
}