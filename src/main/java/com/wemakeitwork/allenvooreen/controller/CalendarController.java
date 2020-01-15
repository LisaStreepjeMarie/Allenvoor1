package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wemakeitwork.allenvooreen.model.*;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CalendarController {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/calendar/{teamId}")
    public String showmyCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal)
            throws JsonProcessingException {
        Team team = teamRepository.getOne(teamId);
        httpSession.setAttribute("team", team);

        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        if(member.isPresent()){
            teamList = member.get().getTeamName();
        }

        List<Medication> medicationList = team.getMedicationList();

        Event event = new Event();
        event.setActivity(new Activity());
        model.addAttribute("teamList", teamList);
        model.addAttribute("event", event);
        model.addAttribute("medicationList", medicationList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        model.addAttribute("calendarData", mapper.writeValueAsString(team.getEventList()));

        return "calendar";
    }

    @GetMapping("/home")
    public String calendar(Model model, Principal principal){
        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMemberName(principal.getName());
        if(member.isPresent()){
            teamList = member.get().getTeamName();
        }
        if (teamList != null) {
            model.addAttribute("teamList", teamList);
        }
        return "home";
    }
}
