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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CalendarController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/calendar")
    public String calendar(Model model, Principal principal) throws JsonProcessingException {
        List<Event> eventList = new ArrayList<>();
        Optional<Member> member = memberRepository.findByMembername(principal.getName());
        Team team = new Team();
        if(member.isPresent()){
            if (teamRepository.findTeamIdByMemberid(member.get().getMemberId()) != null) {
                team = teamRepository.findTeamById(teamRepository.findTeamIdByMemberid(member.get().getMemberId()));
                eventList = team.getEventList();
            }
        }

        String calendarData = "";
        for (Event event : eventList) {
            calendarData += "" + new ObjectMapper().writeValueAsString(event) + ",";
        }
        Event event = new Event();
        event.setActivity(new Activity());
        model.addAttribute("event", new Event());
        model.addAttribute("calendarData", calendarData);
        return "calendar";
    }
}
