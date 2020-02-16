package com.wemakeitwork.allenvooreen.model;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemberTest {
    Member testMember = new Member();

    @Test
    void getUsername() {
        //arrange
        testMember.setMemberName("testmember");

        //arrange
        Assertions.assertThat(testMember.getMemberName()).isEqualTo("testmember");
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
        //TODO: set to false before last presentation demo)
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
        testMember.setMemberName("testmember");

        //arrange
        Assertions.assertThat(testMember.getMemberName()).isEqualTo("testmember");
    }

    @Test
    void setMembername() {
        //arrange
        testMember.setMemberName("testmember");

        //arrange
        Assertions.assertThat(testMember.getMemberName()).isEqualTo("testmember");
    }

    // Test builder pattern
    @Test
    public void testMemberBuilder() {
        // Arrange: set testvalues
        Integer memberId = 1;
        String memberName = "testMember";
        String password = "password123456";
        String rol = "admin";
        String email = "testmember@allenvooreen.ddns.net";
        boolean enabled = true;

        // Act: Create testmember using builder pattern
        Member testMember = new Member.Builder()
                .setMemberId(memberId)
                .setMemberName(memberName)
                .setPassword(password)
                .setRol(rol)
                .setEmail(email)
                .setEnabled(enabled)
                .build();

        // Assert: check values of created testmember
        Assert.assertEquals(testMember.getMemberId(), memberId);
        Assert.assertEquals(testMember.getMemberName(), memberName);
        Assert.assertEquals(testMember.getPassword(), password);
        Assert.assertEquals(testMember.getRol(), rol);
        Assert.assertEquals(testMember.getEmail(), email);
        Assert.assertEquals(testMember.isEnabled(), enabled);
    }

    @AfterEach
    void cleanUp(){
        testMember = null;
    }
}