package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class TeamRepositoryIntegrationTest {
    Team testTeam = new Team();

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void createTeam(){
        testTeam.setTeamName("testMember");
        testTeam.setTeamId(2);
    }

    @Test
    public void whenFindByActivityName_thenReturnActivity() {
        // given
        teamRepository.save(testTeam);

        // when
        Optional<Team> result = teamRepository.findByTeamName(testTeam.getTeamName());

        // then
        result.ifPresent(team -> assertThat(team.getTeamName())
                .isEqualTo(testTeam.getTeamName()));
    }

    @org.junit.Test
    public void deleteTeamTest(){
        // given
        teamRepository.save(testTeam);

        // when
        teamRepository.delete(testTeam);

        // then
        Assertions.assertThat(teamRepository.findById(2))
                .isEqualTo(Optional.empty());
    }

}