package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DeleteEventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/event/delete/{eventId}")
    public String deleteEvent(@PathVariable("eventId") final Integer eventId) {
        eventRepository.deleteById(eventId);
        return "redirect:/calendar";
    }
}
