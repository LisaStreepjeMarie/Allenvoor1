package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryIntegrationTest {


    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void whenFindByActivityName_thenReturnActivity() {
        // given
        Member testMember = new Member();
        testMember.setMembername("testMember");
        testMember.setMemberId(3);
        memberRepository.save(testMember);

        // when
        Optional<Member> result = memberRepository.findByMembername(testMember.getMembername());

        // then
        result.ifPresent(member -> Assertions.assertThat(member.getMembername())
                .isEqualTo(testMember.getMembername()));
    }

}
