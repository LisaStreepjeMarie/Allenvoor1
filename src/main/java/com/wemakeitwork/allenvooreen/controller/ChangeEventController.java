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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChangeEventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;


    @GetMapping("/event/change")
    protected String showChangeEventForm(Model model) {
        List<Activity> activityList = activityRepository.findAll();
        for (Activity activity : activityList) {
            System.out.println(activity.getActivityId());
        }
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
            activity.setActivityCategory(activity.getActivityCategory());
            event.setActivity(activity);
            event.setEventName(event.getEventName());
            event.setEventId(event.getEventId());
            event.setEventDate(event.getEventDate());
            event.setEventComment(event.getEventComment());
            eventRepository.save(event);
            return "redirect:/event/change";
        }
    }
}
