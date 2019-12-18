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

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class NewEventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    @PostMapping("/event/new")
    protected String saveOrUpdateEvent(@ModelAttribute("event") Event event, Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "newEvent";
        }
        else {
            //N.B.: activityname == eventname for now
            activity.setActivityName(event.getEventName());
            event.setActivity(activity);
            eventRepository.save(event);
            return "redirect:/event/new";
        }
    }
}
