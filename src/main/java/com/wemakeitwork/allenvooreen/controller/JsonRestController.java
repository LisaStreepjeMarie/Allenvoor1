package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JsonRestController {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping(value = "/calendar/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getEvent(){
        Team team = teamRepository.getOne(1);
        List<Event> eventList = team.getEventList();
        return eventList.get(1);
    }
}
