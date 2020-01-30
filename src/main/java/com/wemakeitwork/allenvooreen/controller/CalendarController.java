package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.*;
import com.wemakeitwork.allenvooreen.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CalendarController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @PostMapping("/calendar/new/event")
    public ResponseEntity<Object> newEvent(@RequestBody String newEventJson) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Event event = mapper.readValue(newEventJson, Event.class);
        System.out.println(event.getEventId());

        // this sets the activity to the medication from the activity
        if (event.getActivity() instanceof MedicationActivity){
            setActivityToMedication(event);
        }

        // removes any medication amount if the old activity was a medical one
        if (event.getEventId() != null){
            removeMedicationAmountFromActivity(event);
        }

        eventRepository.save(event);
        ServiceResponse<Event> result = new ServiceResponse<Event>("Succes", event);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    @PostMapping("/calendar/change/eventdate")
    public ResponseEntity<Object> changeEventDate(@RequestBody String newDatesJson) throws JsonProcessingException {
        Event newDatesEvent = mapper.readValue(newDatesJson, Event.class);

        // Get the existing event from database, and set the new dates on it
        Event existingEvent = eventRepository.getOne(newDatesEvent.getEventId());
        existingEvent.setEventStartDate(newDatesEvent.getEventStartDate());
        existingEvent.setEventEndDate(newDatesEvent.getEventEndDate());

        ServiceResponse<Event> response = new ServiceResponse<Event>("success", eventRepository.save(existingEvent));
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/event/delete")
    public ResponseEntity<Object> deleteEvent(@RequestBody String deleteEventJson) throws JsonProcessingException {

        Event eventToDelete = mapper.readValue(deleteEventJson, Event.class);

        // if the event to delete is a medical one, the medication amount needs to be balanced
        removeMedicationAmountFromActivity(eventToDelete);

        eventRepository.deleteById(eventToDelete.getEventId());
        return new ResponseEntity<Object>(eventToDelete, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamid}/medications")
    public ResponseEntity<Object> getMedications(@PathVariable("teamid") final Integer teamId) {
        ServiceResponse<List<Medication>> response = new ServiceResponse<>("success", teamRepository.getOne(teamId).getMedicationList());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/calendar/{teamId}")
    public String showCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal)
            throws JsonProcessingException {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);
        Set<Team> teamList = memberRepository.findByMemberName(principal.getName()).get().getAllTeamsOfMemberSet();
        model.addAttribute("teamList", teamList);

        List<Event> sourceCalendarData = team.getEventList();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String calendarData = mapper.writeValueAsString(sourceCalendarData);

        model.addAttribute("calendarData", calendarData);
        return "calendar";
    }

    private void setActivityToMedication(Event event){
        Optional<Medication> medication = medicationRepository.findById(((MedicationActivity) event.getActivity())
                .getMedication().getMedicationId());
        medication.ifPresent(value -> value.setTakenMedications((MedicationActivity) event.getActivity()));
        medicationRepository.save(medication.get());
    }

    private void removeMedicationAmountFromActivity(Event event){
        eventRepository.findById(event.getEventId()).stream()
                .filter(x -> x.getActivity() instanceof MedicationActivity)
                .forEach(x -> ((MedicationActivity) x.getActivity()).getMedication().removalActivityAddedAmount
                        (((MedicationActivity) x.getActivity()).getTakenMedication()));
    }
}
