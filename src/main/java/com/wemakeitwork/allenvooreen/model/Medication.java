package com.wemakeitwork.allenvooreen.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Medication {

    //TODO made this able to be nullable since it's a subclass of activity. Is this the right way? Who knows!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicationId;

    private String medicationName;

    private Integer medicationAmount;

    private String medicationComment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medication")
    private List<MedicationActivity> takenMedications;

    public void medicationTaken(Integer integer){
        this.medicationAmount -= integer;
    }

    public Integer getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Integer medicationId) {
        this.medicationId = medicationId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Integer getMedicationAmount() {
        return medicationAmount;
    }

    public void setMedicationAmount(Integer medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    public String getMedicationComment() {
        return medicationComment;
    }

    public void setMedicationComment(String medicationComment) {
        this.medicationComment = medicationComment;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
