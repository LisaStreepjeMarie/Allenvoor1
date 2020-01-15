package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;

import java.util.Optional;

public interface MemberService {
    void save(Member member);

    Optional<Member> findByMembername(String membername);
}
