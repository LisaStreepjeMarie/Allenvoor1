package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return memberRepository.findByMemberName(s).orElseThrow(
                () -> new UsernameNotFoundException("Gebruiker " + s + " niet gevonden")
        );
    }
}





