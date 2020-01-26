package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
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
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/calendar/get/{teamid}/{startdate}/{enddate}")
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

    @PostMapping("/calendar/new/event")
    public ResponseEntity<Object> addEvent(@RequestBody String json) throws JsonProcessingException {
        Event event = mapper.readValue(json, Event.class);
        System.out.println("Eventname: " + event.getEventName());
        System.out.println("Activityname: " + event.getActivity().getActivityName());
        if (event.getActivity() instanceof MedicationActivity){
            System.out.println("Activiteitstype is: MedicationActivity");
        } else {
            System.out.println("Activiteitstype is: Vrije tijd");
        }
        /*eventRepository.save(event);*/

        ServiceResponse<Event> response = new ServiceResponse<Event>("success", event);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/calendar/change/eventdate")
    public ResponseEntity<Object> changeEventDate(@RequestBody String json) throws JsonProcessingException {
        Event newDates = mapper.readValue(json, Event.class);

        Event event = eventRepository.getOne(newDates.getEventId());
        event.setEventStartDate(newDates.getEventStartDate());
        event.setEventEndDate(newDates.getEventEndDate());
        eventRepository.save(event);

        ServiceResponse<Event> response = new ServiceResponse<Event>("success", newDates);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamid}/medications")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        ServiceResponse<List<Medication>> response = new ServiceResponse<>("success", teamRepository.getOne(teamId).getMedicationList());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
