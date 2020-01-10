package com.wemakeitwork.allenvooreen.model;

import javax.persistence.Entity;

@Entity
public class MedicationActivity extends Activity{
    private Integer takenMedication;

    public Integer getTakenMedication() {
        return takenMedication;
    }

    public void setTakenMedication(Integer takenMedication) {
        this.takenMedication = takenMedication;
    }
}
