package com.wemakeitwork.allenvooreen.model;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teamId;

    @Column(name = "teamName")
    private String teamName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "memberId"), inverseJoinColumns = @JoinColumn(name = "teamMemberId"))
    private Set<Member> membername;

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
}
