package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken (String token);

    VerificationToken findByMember(Member member);

}
