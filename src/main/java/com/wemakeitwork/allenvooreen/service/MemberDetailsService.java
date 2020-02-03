package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public Member loadUserByUsername(String s) throws UsernameNotFoundException {
        return memberRepository.findByMemberName(s).orElseThrow(
                () -> new UsernameNotFoundException("Gebruiker " + s + " niet gevonden")
        );
    }
}