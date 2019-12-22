package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonPropertyOrder(value = {"id","title","description","start", "end"}, alphabetic = true)
@JsonIgnoreProperties({ "activity" })
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Integer eventId;

    @JsonProperty("title")
    private String eventName;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("start")
    private java.util.Date eventStartDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("end")
    private java.util.Date eventEndDate;

    @JsonProperty("description")
    private String eventComment;

    //TODO: cascade needs to not be ALL when event and activity planning are split up (in future releases).

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "activityId", referencedColumnName = "activityId", nullable = false)
    private Activity activity;

    @JsonGetter
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @JsonGetter
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @JsonGetter
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @JsonGetter
    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    @JsonGetter
    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    @JsonGetter
    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }
}
