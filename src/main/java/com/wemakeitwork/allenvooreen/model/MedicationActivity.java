package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        //include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Medication.class, name = "medication")
})
public class MedicationActivity extends Activity{

    @JsonProperty("takenmedication")
    @Column(nullable = false)
    private Integer takenMedication;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicationId", referencedColumnName = "medicationId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty("medication")
    private Medication medication;

    public MedicationActivity(Integer activityId, String activityName, @Nullable Integer takenMedication, @Nullable Medication medication) {
        super(activityId, activityName);
        this.takenMedication = takenMedication;
        this.medication = medication;
    }

    public MedicationActivity(@Nullable Integer takenMedication, @Nullable Medication medication) {
        this.takenMedication = takenMedication;
        this.medication = medication;
    }

    public MedicationActivity() {
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
