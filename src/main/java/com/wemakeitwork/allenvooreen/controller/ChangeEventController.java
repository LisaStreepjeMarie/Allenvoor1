package com.wemakeitwork.allenvooreen.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

import javax.servlet.jsp.tagext.Tag;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class ChangeEventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/event/all")
    protected String showTeams(Model model) {
        model.addAttribute("allEvents", eventRepository.findAll());
        return "eventOverview";
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
            activity.setActivityName(event.getEventName());
            event.setActivity(activity);
            eventRepository.save(event);
            return "redirect:/event/all";
        }
    }

    @GetMapping("/event/select/{eventId}")
    protected String showEvent(@PathVariable("eventId") final Integer eventId, Model model) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Event event;
        event = eventOptional.orElseGet(Event::new);

        Optional<Activity> activityOptional = activityRepository.findById(event.getActivity().getActivityId());
        Activity activity;
        activity = activityOptional.orElseGet(Activity::new);

        model.addAttribute("activityId", activity.getActivityId());
        model.addAttribute("event", event);
        return "changeEvent";
    }

}

