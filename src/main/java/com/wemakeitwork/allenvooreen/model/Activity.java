package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        //include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MedicationActivity.class, name = "MedicationActivity")
})
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer activityId;

    @JsonProperty("name")
    private String activityName;

    @JsonProperty("category")
    private String activityCategory;

    public Activity(Integer activityId, String activityName, String activityCategory) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityCategory = activityCategory;
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

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }
}
