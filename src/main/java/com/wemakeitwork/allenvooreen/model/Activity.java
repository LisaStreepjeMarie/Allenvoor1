package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MedicationActivity.class, name = "MedicationActivity"),
        @JsonSubTypes.Type(value = LeisureActivity.class, name = "LeisureActivity")
})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "activity")
public abstract class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer activityId;

    @JsonProperty("name")
    private String activityName;

    @OneToOne(mappedBy = "activity")
    @JsonIgnore
    private Event event;

    public Activity(Integer activityId, String activityName) {
        this.activityId = activityId;
        this.activityName = activityName;
    }

    public Activity() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
