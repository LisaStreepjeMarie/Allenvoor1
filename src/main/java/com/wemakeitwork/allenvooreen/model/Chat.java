package com.wemakeitwork.allenvooreen.model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Chat {

    private int chatId;

    private Team team;

    private List<Message> messages;
}
