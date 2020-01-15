package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties({ "medication", "takenMedication" })
public class MedicationActivity extends Activity{

    @Nullable
    private Integer takenMedication;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicationId", referencedColumnName = "medicationId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Medication medication;

    public MedicationActivity() {
        this.setActivityCategory("Medisch");
    }

    public Integer getTakenMedication() {
        return takenMedication;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public void setTakenMedication(Integer takenMedication) {
        this.takenMedication = takenMedication;
    }
}
