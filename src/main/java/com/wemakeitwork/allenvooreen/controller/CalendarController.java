package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.Team;
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


    @PostMapping("/calendar/new/event")
    public ResponseEntity<Object> newEvent(@RequestBody String newEventJson) throws JsonProcessingException {
        return new ResponseEntity<Object>(new ServiceResponse<Event>("success",
                eventRepository.save(mapper.readValue(newEventJson, Event.class))), HttpStatus.OK);
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

    @GetMapping("/calendar/{teamId}")
    public String showCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal)
            throws JsonProcessingException {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);
        Set<Team> teamList = memberRepository.findByMemberName(principal.getName()).get().getAllTeamsOfMemberSet();
        model.addAttribute("teamList", teamList);
        for (Medication medication : team.getMedicationList()) {
            System.out.println("MEDICATIE: " + medication.getMedicationName());
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        model.addAttribute("calendarData", mapper.writeValueAsString(team.getEventList()));
        return "calendar";
    }
}
