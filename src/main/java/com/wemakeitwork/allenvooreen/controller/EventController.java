package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.EventSubscription;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.EventSubscriptionRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
public class EventController {
    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventSubscriptionRepository eventSubscriptionRepository;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    //TODO not sure if the mapping works correctly here
    protected void newEventWithMedicationActivity(Event event) {
        //tried out a stream to get the correct medication and set medication activity
        medicationRepository.findAll().stream()
                .filter(x -> {
                    assert ((MedicationActivity) event.getActivity()).getMedication() != null;
                    return x.getMedicationId() == ((MedicationActivity) event.getActivity()).getMedication().getMedicationId();
                })
                .forEach(x -> x.setTakenMedications((MedicationActivity) event.getActivity()));
        event.getTeam().getTeamId();
    }

    // Allow empty string in datefield (i.e. write them as NULL to database)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/event/new")
    protected String newEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity")
            MedicationActivity medicationActivity, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam((Team) httpSession.getAttribute("team"));
            event.getActivity().setActivityName(event.getEventName());

            if (event.getActivity() instanceof MedicationActivity){
                if (medicationActivity.getMedication() == null) {
                    return "redirect:/calendar/" + team.getTeamId();
                }else {
                    event.setActivity(medicationActivity);
                    newEventWithMedicationActivity(event);
                }
            }

            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }

    @GetMapping("/event/{eventid}/subscriptionlist")
    protected String showSubscriptionList(@PathVariable("eventid") final Integer eventId, Model model) {
        Set<EventSubscription> eventSubscriptionSet = eventSubscriptionRepository.findByEventEventId(eventId);
        if (eventSubscriptionSet.size() != 0) {
            for (EventSubscription eventSubscription : eventSubscriptionSet) {
                System.out.println(eventSubscription.getMember());
            }
        }
        model.addAttribute("eventSubscriptionSet", eventSubscriptionSet);
        return "subscriptionList";
    }
}
