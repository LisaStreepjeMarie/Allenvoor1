package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class NewActivityController {

    @Autowired
    ActivityRepository activityRepository;

    //TODO in this sprint we don't need this form. Remember to link this back to the event


    @GetMapping("/activity/new")
    protected String showActivityForm(Model model) {
        model.addAttribute("activity", new Activity());
        return "newActivity";
    }

    @PostMapping("/activity/new")
    protected String saveOrUpdateActivity(@ModelAttribute("activity") Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "newActivity";
        }
        else {
            activityRepository.save(activity);
            return "redirect:/activity/new";
        }
    }
}
