package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "membership")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
public class TeamMembership {
    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipId;

    @JsonProperty("team")
    @ManyToOne(fetch= FetchType.EAGER)
    private Team team;

    @JsonProperty("member")
    @ManyToOne(fetch= FetchType.EAGER)
    private Member member;

    boolean isAdmin = false;

    public TeamMembership(Team team, Member member, boolean isAdmin) {
        this.team = team;
        this.member = member;
        this.isAdmin = isAdmin;
    }

    public TeamMembership() {
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }
}
