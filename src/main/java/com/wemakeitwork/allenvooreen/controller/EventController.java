package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.*;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.EventRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import com.wemakeitwork.allenvooreen.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    //TODO this needs to be cleaned up the code makes me sad but works - LM
    @PostMapping("/event/change/{activityId}")
    protected String changeEvent(@ModelAttribute("event") Event event, @ModelAttribute("medicationActivity") MedicationActivity
                                 medicationActivity, @PathVariable("activityId") final Integer activityId, BindingResult result) {
        if (result.hasErrors()) {
            return "calendar";
        } else {

            //setting the team to the event
            Team team = (Team) httpSession.getAttribute("team");
            event.setTeam(team);

            //checking if the event is medical to set the subclass
            if (event.getActivity().getActivityCategory().equals("Medisch")){
                event.setActivity(medicationActivity);
            }

            //arranging some stuff to adjust the medication amount from the orig activity med amount if available
            int takenMedication = 0;
            Optional<Activity> activity = activityRepository.findById(activityId);
            Medication originalMedication = new Medication();

            if (activity.isPresent() && activity.get() instanceof MedicationActivity){
                takenMedication = ((MedicationActivity) activity.get()).getTakenMedication();
                originalMedication = ((MedicationActivity) activity.get()).getMedication();
            }

            event.getActivity().setActivityId(activityId);
            event.getActivity().setActivityName(event.getEventName());

            //adjusting the medication amount and setting the right activity on the medication again.
            if(event.getActivity() instanceof MedicationActivity){
                Optional<Medication> medication = medicationRepository.findById(medicationActivity.getMedication().getMedicationId());
                if(medication.isPresent() && medication.get() == originalMedication){
                    medication.get().removalActivityAddedAmount(takenMedication);
                }
                medication.ifPresent(value -> value.setTakenMedications(medicationActivity));
            }

            eventRepository.save(event);
            return "redirect:/calendar/" + team.getTeamId();
        }
    }
}
