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


    @GetMapping("/event/all")
    protected String showTeams(Model model){
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
            return "redirect:/event/change";
        }
    }

    @GetMapping("/event/select/{eventId}")
    protected String showEvent(@PathVariable("eventId") final Integer eventId, Model model) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Event event;
        if (eventOptional.isPresent()) {
            event = eventOptional.get();
        } else {
            event = new Event();
        }

        Optional<Activity> activityOptional = activityRepository.findById(event.getActivity().getActivityId());
        Activity activity;
        if (activityOptional.isPresent()) {
            activity = activityOptional.get();
        } else {
            activity = new Activity();
        }
        System.out.println("DIS IS ZE AKTIVITI ID: " + activity.getActivityId());

        model.addAttribute("activityId", activity.getActivityId());
        model.addAttribute("event", event);
        return "changeEvent";
    }

}
