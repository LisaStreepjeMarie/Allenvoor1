package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@JsonPropertyOrder(value = {"id", "title", "description", "start", "end", "donedate"}, alphabetic = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Activity.class, name = "activity"),
})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer eventId;

    @JsonProperty("title")
    @NotBlank(message = "veld mag niet blank zijn")
    private String eventName;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("start")
    private java.util.Date eventStartDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("end")
    private java.util.Date eventEndDate;

    @JsonProperty("comment")
    private String eventComment;

    @JsonProperty("donedate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.util.Date eventDoneDate;

    @OneToOne(mappedBy = "event", orphanRemoval=true, cascade={CascadeType.ALL, CascadeType.PERSIST})
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    public Event() {
    }

    public Event(Integer eventId, @NotBlank(message = "veld mag niet blank zijn") String eventName, Date eventStartDate, Date eventEndDate, String eventComment, Date eventDoneDate, Activity activity, Team team) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventComment = eventComment;
        this.eventDoneDate = eventDoneDate;
        this.activity = activity;
        this.team = team;
    }

    public Date getEventDoneDate() {
        return eventDoneDate;
    }

    public void setEventDoneDate(Date eventDoneDate) {
        this.eventDoneDate = eventDoneDate;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
