package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@JsonPropertyOrder(value = {"id", "title", "description", "start", "end", "donedate"}, alphabetic = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Activity.class, name = "activity"),
})
@Table(name = "events")
public class Event {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty("doneByMember")
    private Member doneByMember;

    @JsonProperty("comment")
    private String eventComment;

    @JsonProperty("interval")
    private String eventInterval;

    @JsonProperty("maxNumber")
    private Integer eventMaxNumber;

    @JsonProperty("donedate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.util.Date eventDoneDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<EventSubscription> eventSubscriptions;

    public Event() {
    }
    public Event(eventBuilder builder) {
        this.eventId = builder.eventId;
        this.eventName = builder.eventName;
        this.eventStartDate = builder.eventStartDate;
        this.eventEndDate = builder.eventEndDate;
        this.activity = builder.activity;
        this.team = builder.team;
    }

    public Integer getEventMaxNumber() {
        return eventMaxNumber;
    }

    public String getEventInterval() {
        return eventInterval;
    }

    public Date getEventDoneDate() {
        return eventDoneDate;
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

    public Team getTeam() {
        return team;
    }

    @Nullable
    public Member getDoneByMember() {
        return doneByMember;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public static class eventBuilder {
        private Integer eventId;
        private String eventName;
        private java.util.Date eventStartDate;
        private java.util.Date eventEndDate;
        private Member doneByMember;
        private String eventComment;
        private String eventInterval;
        private Integer eventMaxNumber;
        private java.util.Date eventDoneDate;
        private Activity activity;
        private Team team;

        public eventBuilder(Integer eventId, String eventName, Date eventStartDate, Date eventEndDate, Activity activity, Team team) {
            this.eventId = eventId;
            this.eventName = eventName;
            this.eventStartDate = eventStartDate;
            this.eventEndDate = eventEndDate;
            this.activity = activity;
            this.team = team;
        }

        public eventBuilder setDoneByMember(@Nullable Member doneByMember) {
            this.doneByMember = doneByMember;
            return this;
        }

        public eventBuilder setEventComment(String eventComment) {
            this.eventComment = eventComment;
            return this;
        }

        public eventBuilder setEventInterval(String eventInterval) {
            this.eventInterval = eventInterval;
            return this;
        }

        public eventBuilder setEventMaxNumber(Integer eventMaxNumber) {
            this.eventMaxNumber = eventMaxNumber;
            return this;
        }

        public eventBuilder setEventDoneDate(Date eventDoneDate) {
            this.eventDoneDate = eventDoneDate;
            return this;
        }

        public Event build(){
            return new Event(this);
        }

    }
}
