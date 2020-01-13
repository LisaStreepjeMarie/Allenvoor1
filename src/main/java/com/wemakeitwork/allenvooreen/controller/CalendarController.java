package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CalendarController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @GetMapping("/calendar/{teamId}")
    public String showmyCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal) throws JsonProcessingException {
        Team team = teamRepository.findTeamById(teamId);
        List<Event> eventList = team.getEventList();
        httpSession.setAttribute("team", team);

        String calendarData = "";
        for (Event event : eventList) {
            calendarData += "" + new ObjectMapper().writeValueAsString(event) + ",";
        }

        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        if(member.isPresent()){
            teamList = memberRepository.getOne(member.get().getMemberId()).getTeamName();

        }

        //trying out lambda and streams below to load the correct medication for the team
        List<Medication> medicationList = new ArrayList<>();
        medicationRepository.findAll().stream()
                .filter(x -> x.getTeam() == team)
                .forEach(medicationList::add);

        model.addAttribute("teamList", teamList);
        model.addAttribute("calendarData", calendarData);
        model.addAttribute("medicationList", medicationList);
        return "calendar";
    }


    @GetMapping("/home")
    public String calendar(Model model, Principal principal){
        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        if(member.isPresent()){
            teamList = memberRepository.getOne(member.get().getMemberId()).getTeamName();

        }
        model.addAttribute("teamList", teamList);
        return "home";
    }
}
