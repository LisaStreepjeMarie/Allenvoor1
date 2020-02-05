package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({ "medicationAmount", "medicationComment", "team", "getTakenMedications", "hibernateLazyInitializer"})
public class Medication {

    public Medication() {
        this.bought = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer medicationId;

    @JsonProperty("name")
    private String medicationName;

    @JsonProperty("amount")
    private Integer medicationAmount;

    @JsonProperty("refillamount")
    private int medicationRefillAmount;

    private String medicationComment;

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grocerylistId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GroceryList groceryList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "medication")
    private List<MedicationActivity> takenMedications;

    @JsonProperty("bought")
    private Boolean bought;

    public Boolean getBought() {
        return bought;
    }

    public void setBought(boolean bought){
        this.bought = bought;
    }

    public void toggleBought() {
        this.bought = !this.bought;
    }

    public Integer getMedicationId() {
        return medicationId;
    }

    public List<MedicationActivity> getTakenMedications() {
        return takenMedications;
    }

    public void setTakenMedications(MedicationActivity medicationActivity) {
        this.takenMedications.add(medicationActivity);
        this.medicationAmount -= medicationActivity.getTakenMedication();
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

    public void upTheMedicationAmount(Integer integer){
        this.medicationAmount += integer;
    }

    public int getMedicationRefillAmount() {
        return medicationRefillAmount;
    }

    public void upTheRefillAmount(Integer integer){
        this.medicationRefillAmount += integer;
    }

    public void setMedicationRefillAmount(int medicationRefillAmount) {
        this.medicationRefillAmount = medicationRefillAmount;
    }
}
