package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CalendarController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private HttpSession httpSession;

    public CalendarController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/calendar/{teamId}")
    public String showmyCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal)
            throws JsonProcessingException {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);
        Set<Team> teamList = memberRepository.findByMemberName(principal.getName()).get().getAllTeamsOfMemberSet();

        ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                .sorted(Comparator.comparing(Team::getTeamName))
                .collect(Collectors.toList());

        sortedList.forEach(x -> System.out.println(x.getTeamName()));

        model.addAttribute("teamList", sortedList);

        return "calendar";
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

    @GetMapping("/home")
    public String calendar(Model model, Principal principal){
        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        if(member.isPresent()){
            teamList = member.get().getAllTeamsOfMemberSet();
        }
        if (teamList != null) {
            ArrayList<Team> sortedList = (ArrayList<Team>) teamList.stream()
                    .sorted(Comparator.comparing(Team::getTeamName))
                    .collect(Collectors.toList());

            sortedList.forEach(x -> System.out.println(x.getTeamName()));
            model.addAttribute("teamList", sortedList);
        }

        return "home";
    }
}
