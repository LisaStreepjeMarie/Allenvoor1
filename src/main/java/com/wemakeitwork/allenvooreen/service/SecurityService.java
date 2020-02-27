package com.wemakeitwork.allenvooreen.service;


import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.PasswordResetToken;
import com.wemakeitwork.allenvooreen.repository.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;

@Service
public class SecurityService implements SecurityServiceInterface {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberDetailsService memberDetailsService;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }
        return null;
    }

    @Override
    public void autoLogin(String memberName, String password, boolean enabled) {
        UserDetails userDetails = memberDetailsService.loadUserByUsername(memberName);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated() && userDetails.isEnabled()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", memberName));
        }else {
        } throw new UsernameNotFoundException("Verifieer je account om door te gaan");
    }

    @Override
    public String validatePasswordResetToken(int id, String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getMember().getMemberId() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        Member member = passToken.getMember();
        Authentication auth = new UsernamePasswordAuthenticationToken(member, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}