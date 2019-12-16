package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    Member testMember = new Member();

    @Test
    void getRol() {
        //arrange
        testMember.setRol("testRol");

        //assert
        Assertions.assertThat(testMember.getRol()).isEqualTo("testRol");
    }

    @Test
    void setRol(){
        //arrange
        testMember.setRol("testRol");

        //assert
        Assertions.assertThat(testMember.getRol()).isEqualTo("testRol");
    }


    @Test
    void getAuthorities() {
        //assert
        Assertions.assertThat(testMember.getAuthorities()).isEqualTo(true);
    }


    @Test
    void getUsername() {
        //arrange
        testMember.setMembername("testmember");

        //arrange
        Assertions.assertThat(testMember.getMembername()).isEqualTo("testmember");
    }

    @Test
    void isAccountNonExpired() {
        //assert
        Assertions.assertThat(testMember.isAccountNonExpired()).isEqualTo(true);
    }

    @Test
    void isAccountNonLocked() {
        //assert
        Assertions.assertThat(testMember.isAccountNonLocked()).isEqualTo(true);
    }

    @Test
    void isCredentialsNonExpired() {
        //assert
        Assertions.assertThat(testMember.isCredentialsNonExpired()).isEqualTo(true);
    }

    @Test
    void isEnabled() {
        //assert
        Assertions.assertThat(testMember.isEnabled()).isEqualTo(true);
    }

    @Test
    void setMemberId() {
        //arrange
        testMember.setMemberId(5);

        //assert
        Assertions.assertThat(testMember.getMemberId()).isEqualTo(5);
    }


    @Test
    void setPassword() {
        //arrange
        testMember.setPassword("supergeheim");

        //assert
        Assertions.assertThat(testMember.getPassword()).isEqualTo("supergeheim");
    }

    @Test
    void getMembername() {
        //arrange
        testMember.setMembername("testmember");

        //arrange
        Assertions.assertThat(testMember.getMembername()).isEqualTo("testmember");
    }

    @Test
    void setMembername() {
        //arrange
        testMember.setMembername("testmember");

        //arrange
        Assertions.assertThat(testMember.getMembername()).isEqualTo("testmember");
    }


    @AfterEach
    void cleanUp(){
        testMember = null;
    }
}