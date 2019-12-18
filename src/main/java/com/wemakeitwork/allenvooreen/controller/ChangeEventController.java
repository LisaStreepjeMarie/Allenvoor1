package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ChangeEventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;


    @GetMapping("/event/select/{eventId}")
    protected String showTeamData(@PathVariable("teamId") final Integer eventId, Model model) {
        // extra regel om te testen:
        model.addAttribute("event", new Event());
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Event event;
        if (eventOptional.isPresent()) {
            event = eventOptional.get();
        } else {
            event = new Event();
        }
        model.addAttribute("event", event);
        return "changeEvent";
    }

    @GetMapping("/event/change")
    protected String showChangeEventForm(Model model) {
        List<Activity> activityList = activityRepository.findAll();
        model.addAttribute(activityList);
        model.addAttribute("event", new Event());
        return "changeEvent";
    }

    @PostMapping("/event/change")
    protected String changeEvent(@ModelAttribute("event") Event event, Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "changeEvent";
        }
        else {
            //N.B.: activityname == eventname for now
            //activity.setActivityName(event.getEventName());
            //event.setActivity(activity);
            //eventRepository.save(event);
            System.out.println("DIT IS DE HUIDIGE EVENT_ID: " + event.getEventId());
            return "redirect:/event/change";
        }
    }
}
