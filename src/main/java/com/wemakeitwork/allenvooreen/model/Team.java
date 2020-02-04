package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@JsonPropertyOrder(value = {"id","name"}, alphabetic = true)
// Ignoring 'hibernateLazyInitializer' & 'handler' is needed to prevent infinite recursion
// when calling the ObjectMapper to create a JSON
@JsonIgnoreProperties({ "allMembersInThisTeamSet", "eventList", "medicationList", "hibernateLazyInitializer", "handler" })
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer teamId;

    @JsonProperty("name")
    private String teamName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "team")
    Set<TeamMembership> teamMemberships;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "team")
    List<Event> eventList = new ArrayList<>();

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "team")
    List<Medication> medicationList = new ArrayList<>();

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(Medication medication) {
        this.medicationList.add(medication);
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

    public Set<TeamMembership> getTeamMemberships() {
        return teamMemberships;
    }

    public void setTeamMemberships(Set<TeamMembership> teamMemberships) {
        this.teamMemberships = teamMemberships;
    }
}
