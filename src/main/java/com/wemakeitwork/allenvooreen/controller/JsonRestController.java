package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JsonRestController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/calendar/json/{teamid}/{startdate}/{enddate}")
    public List<Event> getJsonEventListPeriod(@PathVariable("teamid") final Integer teamId,
                                              @PathVariable("startdate") final long startDateEpoch,
                                              @PathVariable("enddate") final long endDateEpoch) {

        // Return events that occur in the timeperiod the fullcalendar view is currently watching (from one team)
        return teamRepository.getOne(teamId).getEventList().stream()
                // Filter events that occur after the startdate (of fullcalendars view)
                .filter(x -> x.getEventStartDate().after(new Date(startDateEpoch)))
                // Filter events that occur before the enddate (of fullcalendars view)
                .filter(x -> x.getEventEndDate().before(new Date(endDateEpoch)))
                // Return the filtered events
                .collect(Collectors.toList());
    }

    //TODO works but the event isn't saved
    @PostMapping("/calendar/saveEventFromPost")
    public ResponseEntity<Object> addEvent(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = new ObjectMapper().readTree(json);

        Activity activity = new Activity();
        activity.setActivityName(jsonNode.get("activity").get("name").textValue());
        activity.setActivityCategory(jsonNode.get("activity").get("category").textValue());
        Team team = new Team();
        team.setTeamId(jsonNode.get("team").get("id").intValue());

        Event event = mapper.readValue(json, Event.class);
        event.setActivity(activity);
        event.setTeam(team);

        eventRepository.save(event);

        ServiceResponse<Event> response = new ServiceResponse<>("success", event);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamid}/medications")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        System.out.println("hello!!");
        ServiceResponse<List<Medication>> response = new ServiceResponse<>("success", teamRepository.getOne(teamId).getMedicationList());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
