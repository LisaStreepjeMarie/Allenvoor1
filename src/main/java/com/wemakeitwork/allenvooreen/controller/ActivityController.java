package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Medication;
import com.wemakeitwork.allenvooreen.model.MedicationActivity;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import com.wemakeitwork.allenvooreen.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    private HttpSession httpSession;

    //TODO in this sprint we don't need this form. Remember to link this back to the event


    @GetMapping("/activity/new")
    protected String showActivityForm(Model model) {
        model.addAttribute("activity", new Activity());
        return "newActivity";
    }

    @GetMapping("/medicationActivity/new")
    protected String showMedicationForm(Model model) {
        Team team = (Team) httpSession.getAttribute("team");
        List<Medication> medicationList = new ArrayList<>();
        medicationRepository.findAll().stream()
                .filter(x -> x.getTeam() == team)
                .forEach(medicationList::add);
        model.addAttribute("medicationList", medicationList);
        model.addAttribute("medicationActivity", new MedicationActivity());
        return "home";
    }

    @GetMapping("/activity/all")
    protected String showTeams(Model model){
        model.addAttribute("allActivities", activityRepository.findAll());
        return "activityOverview";
    }

    @GetMapping("/activity/select/{activityId}")
    protected String showTeamData(@PathVariable("activityId") final Integer activityId, Model model) {

        model.addAttribute("activity", new Activity());

        Optional<Activity> optActivity = activityRepository.findById(activityId);
        Activity activity;
        activity = optActivity.orElseGet(Activity::new);
        model.addAttribute("activity", activity);
        return "activityData";
    }

    @GetMapping("/activity/delete/{activityId}")
    public String deleteTeam(@PathVariable("activityId") final Integer activityId) {
        activityRepository.deleteById(activityId);
        return "redirect:/activity/all";
    }

    @PostMapping("/activity/new")
    protected String saveOrUpdateActivity(@ModelAttribute("activity") Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "newActivity";
        }
        else {
            activityRepository.save(activity);
            return "redirect:/activity/all";
        }
    }

    @PostMapping("/activity/change")
    protected String saveOrUpdateActivity2(@ModelAttribute("activity") Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "activityData";
        } else {
            activityRepository.save(activity);
            return "redirect:/activity/all";
        }
    }
}
