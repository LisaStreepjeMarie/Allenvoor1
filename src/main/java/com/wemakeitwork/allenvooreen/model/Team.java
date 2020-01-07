package com.wemakeitwork.allenvooreen.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
// @Table(name = "team")
public class Team {
    public Team() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId = 0;

    // @Column(name = "teamName")
    private String teamName;

    @ManyToMany
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "memberId"), inverseJoinColumns = @JoinColumn(name = "teamMemberId"))
    private Set<Member> membername = new HashSet<>();

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "team")
    List<Event> eventList = new ArrayList<>();

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

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(Event event) {
        this.eventList.add(event);
    }

}
