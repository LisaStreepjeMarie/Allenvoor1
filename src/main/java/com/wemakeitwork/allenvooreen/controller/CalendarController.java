package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
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
import java.util.Set;

@Controller
public class CalendarController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HttpSession httpSession;

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

    @PostMapping("/calendar/change/event/{eventId}")
    protected ResponseEntity<Object> changeEvent(@RequestBody String changedEventJson,
                                                 @PathVariable("eventId") final Integer eventId) throws JsonProcessingException {
        Event changedEvent = mapper.readValue(changedEventJson, Event.class);
        Event oldEvent = eventRepository.getOne(changedEvent.getEventId());
        activityRepository.delete(oldEvent.getActivity());

        return new ResponseEntity<Object>(new ServiceResponse<Event>("success",
                eventRepository.save(changedEvent)), HttpStatus.OK);
    }

    @PostMapping("/event/delete")
    public ResponseEntity<Object> deleteEvent(@RequestBody String deleteEventJson) throws JsonProcessingException {
        Team team = (Team) httpSession.getAttribute("team");

        Event eventToDelete = mapper.readValue(deleteEventJson, Event.class);

        // Stream to remove the taken amount from the medication if the event is deleted
        activityRepository.findAll().stream()
                .filter(x -> x.getActivityId() == eventRepository.getOne(eventToDelete.getEventId()).getActivity().getActivityId())
                .filter(x -> x instanceof MedicationActivity)
                .forEach(x -> {
                    assert ((MedicationActivity) x).getMedication() != null;
                    ((MedicationActivity) x).getMedication().removalActivityAddedAmount(((MedicationActivity) x).getTakenMedication());
                });

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
}
