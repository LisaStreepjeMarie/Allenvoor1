package com.wemakeitwork.allenvooreen.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MedicationActivity extends Activity{

    @Nullable
    @JsonProperty("takenmedication")
    private Integer takenMedication;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicationId", referencedColumnName = "medicationId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty("medication")
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
