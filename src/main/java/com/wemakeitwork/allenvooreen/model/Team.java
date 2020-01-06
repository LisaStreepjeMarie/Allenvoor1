package com.wemakeitwork.allenvooreen.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
// @Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId = 0;

    // @Column(name = "teamName")
    private String teamName;

    @ManyToMany
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "memberId"), inverseJoinColumns = @JoinColumn(name = "teamMemberId"))
    public Set<Member> membername = new HashSet<>();

    public Set<Member> getMembername() {
        return membername;
    }

    public void setMembername(Set<Member> membername) {
        this.membername = membername;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String setTeamName(String teamName) {
        if (teamName != null && teamName.isEmpty()) {
            return null;
        } else {
            return this.teamName = teamName;
        }
    }

    /* @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", membername=" + membername +
                '}';
    } */
}
