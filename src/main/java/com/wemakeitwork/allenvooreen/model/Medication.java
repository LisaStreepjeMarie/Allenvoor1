package com.wemakeitwork.allenvooreen.model;

import javax.persistence.Entity;

@Entity
public class Medication extends Activity{
    private String medicationName;

    public String getMedicationName() {
        return medicationName;
    }

    public Medication() {
        this.medicationName = super.getActivityName();
    }

    public Integer getMedicationAmount() {
        return medicationAmount;
    }

    public void setMedicationAmount(Integer medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    private Integer medicationAmount;
}
