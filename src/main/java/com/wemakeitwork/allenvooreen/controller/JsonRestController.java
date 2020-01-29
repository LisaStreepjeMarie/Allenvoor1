package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
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
    ActivityRepository activityRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private EventRepository eventRepository;

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
        //splitting the json between objects
        String[] jsonSplit = json.split("SPLIT",2);
        System.out.println(json);
        //creating an event from the first part of the json string
        Event event = mapper.readValue(jsonSplit[0], Event.class);

//        if (event.getEventId() != null){
//            eventRepository.findById(event.getEventId()).stream()
//                    .filter(x -> x.getActivity() instanceof MedicationActivity)
//                    .forEach(x -> ((MedicationActivity) x.getActivity()).getMedication().removalActivityAddedAmount(
//                            ((MedicationActivity) x.getActivity()).getTakenMedication()));
//        }

        //creating a medicationActivity if it's needed from the second part
        // will look into setting takenmedication in the event model to be triggered when a medicationActivity is being added to an event
        if(event.getActivity().getActivityCategory().equals("Medisch")){
            MedicationActivity medicationActivity = mapper.readValue(jsonSplit[1], MedicationActivity.class);
            medicationRepository.findAll().stream()
                    .filter(x -> x.getMedicationName().equals(medicationActivity.getMedication().getMedicationName()))
                    .forEach(x -> medicationActivity.setMedication(x));
            medicationActivity.getMedication().setTakenMedications(medicationActivity);
            event.setActivity(medicationActivity);
        }

//        eventRepository.save(event);
        ServiceResponse<Event> response = new ServiceResponse<Event>("success", event);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamid}/medications")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        ServiceResponse<List<Medication>> response = new ServiceResponse<>("success", teamRepository.getOne(teamId).getMedicationList());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/calendar/change/eventdate")
    public ResponseEntity<Object> changeEventDate(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Event newDates = mapper.readValue(json, Event.class);
        Event event = eventRepository.getOne(newDates.getEventId());
        event.setEventStartDate(newDates.getEventStartDate());
        event.setEventEndDate(newDates.getEventEndDate());
        eventRepository.save(event);
        ServiceResponse<Event> response = new ServiceResponse<Event>("success", newDates);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }



}
