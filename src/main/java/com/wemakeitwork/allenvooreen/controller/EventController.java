package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    public String deleteEvent(@PathVariable("eventId") final Integer eventId,
                              @PathVariable("activityId") final Integer activityId) {
        Team team = (Team) httpSession.getAttribute("team");

        Optional<Activity> activity = activityRepository.findById(activityId);

        if (activity.get() instanceof MedicationActivity){
            Medication medication = ((MedicationActivity) activity.get()).getMedication();
            assert medication != null;
            medication.removalActivityAddedAmount(((MedicationActivity) activity.get()).getTakenMedication());
        }

        eventRepository.deleteById(eventId);
        try {
            activityRepository.deleteById(activityId);
        } catch (EmptyResultDataAccessException ex) {
            return "redirect:/calendar/" + team.getTeamId();
        }

        // The responsestatus that is preceding this method is necessary to prevent a 500 error.
        return "redirect:/calendar/" + team.getTeamId();
    }

    @PostMapping("/event/new")
    protected String saveOrUpdateEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity")
            MedicationActivity medicationActivity, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        }

        else {
            System.out.println(event.getEventName());
            System.out.println(medicationActivity);
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam(team);

            if (event.getActivity().getActivityCategory().equals("Medisch")){
                event.setActivity(medicationActivity);
            }

            event.getActivity().setActivityName(event.getEventName());


            if(event.getActivity() instanceof MedicationActivity){

                if (medicationActivity.getMedication() == null){
                    //TODO this is not a clean way of solving the medication not null error. It needs to be able to be null for saving superclass
                    //might be nice to solve this with a custom validator in next sprint
                    return "redirect:/calendar/" + team.getTeamId();
                }

                Optional<Medication> medication = medicationRepository.findById(medicationActivity.getMedication().getMedicationId());
                medication.ifPresent(value -> value.setTakenMedications(medicationActivity));
            }
            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }

    @PostMapping("/event/change/{activityId}")
    protected String changeEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity") MedicationActivity
                                 medicationActivity, @PathVariable("activityId") final Integer activityId, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {
            System.out.println(activityId);
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam(team);

            if (event.getActivity().getActivityCategory().equals("Medisch")){
                event.setActivity(medicationActivity);
            }

            event.getActivity().setActivityId(activityId);
            event.getActivity().setActivityName(event.getEventName());

            if(event.getActivity() instanceof MedicationActivity){

                //TODO medication amount needs to be done correctly before changing - LM
//                Optional<Activity> activity = activityRepository.findById(activityId);
//                if(activity.isPresent() && activity.get() instanceof MedicationActivity && ((MedicationActivity) activity.get()).getMedication() == medicationActivity.getMedication()){
//                    Medication medication = ((MedicationActivity) activity.get()).getMedication();
//                    assert medication != null;
//                    medication.removalActivityAddedAmount(((MedicationActivity) activity.get()).getTakenMedication());
//                }

                Optional<Medication> medication = medicationRepository.findById(medicationActivity.getMedication().getMedicationId());
                medication.ifPresent(value -> value.setTakenMedications(medicationActivity));
            }
            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }
}
