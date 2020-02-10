package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "membership")
@JsonIgnoreProperties("team")
public class TeamMembership {
    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipId;

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
