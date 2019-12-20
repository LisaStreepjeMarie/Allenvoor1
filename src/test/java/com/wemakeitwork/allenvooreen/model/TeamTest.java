package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    Team testTeam = new Team();

    @Test
    void getTeamId() {
        //arrange
        testTeam.setTeamId(2);

        //arrange
        Assertions.assertThat(testTeam.getTeamId()).isEqualTo(2);
    }

    @Test
    void setTeamId() {
        //arrange
        testTeam.setTeamId(2);

        //arrange
        Assertions.assertThat(testTeam.getTeamId()).isEqualTo(2);
    }

    @Test
    void getTeamName() {
        //arrange
        testTeam.setTeamName("testTeam");

        //arrange
        Assertions.assertThat(testTeam.getTeamName()).isEqualTo("testTeam");
    }

    @Test
    void setTeamName() {
        //arrange
        testTeam.setTeamName("testTeam");

        //arrange
        Assertions.assertThat(testTeam.getTeamName()).isEqualTo("testTeam");
    }
}