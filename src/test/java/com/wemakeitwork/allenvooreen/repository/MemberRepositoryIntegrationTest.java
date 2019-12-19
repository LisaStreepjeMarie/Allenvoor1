package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryIntegrationTest {
    private Member testMember = new Member();

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void createMember(){
        testMember.setMembername("testMember");
        testMember.setMemberId(3);
    }

    @Test
    public void whenFindByMemberName_thenReturnMember() {
        // given
        memberRepository.save(testMember);

        // when
        Optional<Member> result = memberRepository.findByMembername(testMember.getMembername());

        // then
        result.ifPresent(member -> Assertions.assertThat(member.getMembername())
                .isEqualTo(testMember.getMembername()));
    }

    @Test
    public void deleteMemberTest(){
        // given
        memberRepository.save(testMember);

        // when
        memberRepository.delete(testMember);

        // then
        Assertions.assertThat(memberRepository.findByMembername(testMember.getMembername()))
                .isEqualTo(Optional.empty());
    }

}
