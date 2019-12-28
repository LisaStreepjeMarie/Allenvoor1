package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
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
    ActivityRepository activityRepository;

    @PostMapping("/event/change")
    protected String saveOrUpdateActivity(@ModelAttribute("event") Event event, Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            activity.setActivityName(event.getEventName());
            activity.setActivityId(eventRepository.findActivityIdByEventId(event.getEventId()));
            event.setActivity(activity);
            eventRepository.save(event);
            return "redirect:/calendar";
        }
    }
}

