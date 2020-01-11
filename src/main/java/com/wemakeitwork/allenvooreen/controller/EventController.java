package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    @GetMapping("/event/delete/{eventId}")
    public String deleteEvent(@PathVariable("eventId") final Integer eventId) {
        eventRepository.deleteById(eventId);
        Team team = (Team) httpSession.getAttribute("team");
        return "redirect:/calendar/" + team.getTeamId();
    }

    @PostMapping("/event/new")
    protected String saveOrUpdateEvent(@ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        }
        else {
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam(team);
            event.getActivity().setActivityName(event.getEventName());
            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }

    @PostMapping("/event/change")
    protected String saveOrUpdateActivity(@ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            event.getActivity().setActivityName(event.getEventName());
            //activity loses the ID so below is to get it back
            event.getActivity().setActivityId(eventRepository.findActivityIdByEventId(event.getEventId()));

            //finding/setting the team corresponding with the event
            Team team = teamRepository.getOne(eventRepository.findTeamIdByEventId(event.getEventId()));
            event.setTeam(team);

            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }
}
