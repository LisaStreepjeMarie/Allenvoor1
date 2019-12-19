package com.wemakeitwork.allenvooreen.model;

import javax.persistence.*;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer teamId;
    private String teamName;

    /* @OnDelete(action = OnDeleteAction.CASCADE) */

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
