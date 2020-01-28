package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MedicationActivity.class, name = "MedicationActivity"),
        @JsonSubTypes.Type(value = LeisureActivity.class, name = "LeisureActivity")
})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonProperty("id")
    private Integer activityId;

    @JsonProperty("name")
    private String activityName;

    @OneToOne
    @JoinColumn(name = "activity_id")
    private Event event;

    public Activity(Integer activityId, String activityName) {
        this.activityId = activityId;
        this.activityName = activityName;
    }

    public Activity() {
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
