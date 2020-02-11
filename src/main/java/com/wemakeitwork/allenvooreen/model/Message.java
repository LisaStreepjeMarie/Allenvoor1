package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer messageId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatId", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chat chat;

    @JsonProperty("message")
    private String messageBody;

    @JsonProperty("datePosted")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

}
