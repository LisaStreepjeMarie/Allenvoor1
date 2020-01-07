package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangeEventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TeamRepository teamRepository;

    @PostMapping("/event/change")
    protected String saveOrUpdateActivity(@ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            event.getActivity().setActivityName(event.getEventName());
            //activity loses the ID so below is to get it back
            event.getActivity().setActivityId(eventRepository.findActivityIdByEventId(event.getEventId()));

            //finding/setting the team corresponding with the event
            Team team = teamRepository.findTeamById(eventRepository.findTeamIdByEventId(event.getEventId()));
            event.setTeam(team);

            eventRepository.save(event);
            return "redirect:/calendar";
        }
    }
}

