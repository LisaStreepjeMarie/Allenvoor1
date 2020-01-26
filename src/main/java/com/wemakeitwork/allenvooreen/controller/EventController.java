package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Event;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
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
    private HttpSession httpSession;

    @GetMapping("/event/new")
    protected String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "newEvent";
    }

    @GetMapping("/event/delete/{eventId}/{activityId}")
    public String deleteEvent(@PathVariable("eventId") final Integer eventId,
                              @PathVariable("activityId") final Integer activityId) {
        Team team = (Team) httpSession.getAttribute("team");

        // Stream to remove the taken amount from the medication if the event is deleted
        activityRepository.findAll().stream()
                .filter(x -> x.getActivityId() == activityId)
                .filter(x -> x instanceof MedicationActivity)
                .forEach(x -> {
                    assert ((MedicationActivity) x).getMedication() != null;
                    ((MedicationActivity) x).getMedication().removalActivityAddedAmount(((MedicationActivity) x).getTakenMedication());
                });

        eventRepository.deleteById(eventId);

        return "redirect:/calendar/" + team.getTeamId();
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

    @PostMapping("/event/change/{activityId}")
    protected String changeEvent(@ModelAttribute("event") Event event, BindingResult result,
                                 @ModelAttribute("medicationActivity") MedicationActivity medicationActivity,
                                 @PathVariable("activityId") final Integer activityId) {
        if (result.hasErrors()) {
            return "calendar";
        } else {

            //setting the team to the event
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam(team);

            //checking if the event is medical to set the subclass
            if (event.getActivity() instanceof MedicationActivity){
                event.setActivity(medicationActivity);
                newEventWithMedicationActivity(event);

                //removing the taken medication from the origal medication if there was one in the activity
                //also trying streams
                activityRepository.findAll().stream()
                        //finding the right activity
                        .filter(x -> x.getActivityId() == activityId)
                        //checking if it's a medical activity
                        .filter(x -> x instanceof MedicationActivity)
                        //doing something with the result (in this case removing the medical amount)
                        .forEach(x ->
                        {
                            assert ((MedicationActivity) x).getMedication() != null;
                            ((MedicationActivity) x).getMedication().removalActivityAddedAmount(((MedicationActivity) x).getTakenMedication());
                        });
            }

            event.getActivity().setActivityId(activityId);
            event.getActivity().setActivityName(event.getEventName());
            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }
}
