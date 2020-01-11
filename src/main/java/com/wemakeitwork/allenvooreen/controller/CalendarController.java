package com.wemakeitwork.allenvooreen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
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
    EventRepository eventRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/calendar/{teamId}")
    public String showmyCalender(@PathVariable("teamId") final Integer teamId, Model model, Principal principal) throws JsonProcessingException {
        Team team = teamRepository.getOne(teamId);
        List<Event> eventList = team.getEventList();
        httpSession.setAttribute("team", team);

        String calendarData = "";
        for (Event event : eventList) {
            calendarData += "" + new ObjectMapper().writeValueAsString(event) + ",";
        }

        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        if(member.isPresent()){
            teamList = teamList(member.get().getMemberId());

        }

        Event event = new Event();
        event.setActivity(new Activity());
        model.addAttribute("teamList", teamList);
        model.addAttribute("event", event);
        model.addAttribute("calendarData", calendarData);

        return "calendar";
    }


    @GetMapping("/home")
    public String calendar(Model model, Principal principal){
        Set<Team> teamList = null;
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        if(member.isPresent()){
            teamList = teamList(member.get().getMemberId());

        }
        if (teamList != null) {
            model.addAttribute("teamList", teamList);
        }
        return "home";
    }

    private Set<Team> teamList (Integer memberId){
        Set<Team> teamList;
        Member member = memberRepository.getOne(memberId);
        teamList = member.getTeamName();
        return teamList;
    }
}
