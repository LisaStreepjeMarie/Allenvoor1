package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByMembername(String membername);

    @Override
    Optional<Member> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}


