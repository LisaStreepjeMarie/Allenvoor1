package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class LeisureActivity extends Activity {
    @Nullable
    @JsonProperty("comment")
    private String activityComment;

    public LeisureActivity(Integer activityId, String activityName, String activityComment) {
        super(activityId, activityName);
        this.activityComment = activityComment;
    }

    public LeisureActivity(String activityComment) {
        this.activityComment = activityComment;
    }

    public LeisureActivity(Integer activityId, String activityName) {
        super(activityId, activityName);
    }

    public LeisureActivity() {
    }

    public String getActivityComment() {
        return activityComment;
    }

    public void setActivityComment(String activityComment) {
        this.activityComment = activityComment;
    }
}
