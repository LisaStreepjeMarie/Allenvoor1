package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class NewEventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    @PostMapping("/event/new")
    protected String saveOrUpdateEvent(@ModelAttribute("event") Event event, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "calendar";
        }
        else {
            Optional<Member> member = memberRepository.findByMembername(principal.getName());
            Team team = new Team();
            if(member.isPresent()){
                team = teamRepository.findTeamById(teamRepository.findTeamIdByMemberid(member.get().getMemberId()));
            }
            //N.B.: activityname == eventname for now
            team.setEventList(event);
            event.getActivity().setActivityName(event.getEventName());
            event.setTeam(team);
            eventRepository.save(event);
            return "redirect:/calendar";
        }
    }
}
