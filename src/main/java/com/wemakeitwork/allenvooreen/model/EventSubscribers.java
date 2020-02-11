package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class EventSubscribers {
    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subscriptionId;

    @ManyToOne(fetch= FetchType.EAGER)
    private Event event;

    @JsonProperty("member")
    @ManyToOne(fetch= FetchType.EAGER)
    private Member member;
}
