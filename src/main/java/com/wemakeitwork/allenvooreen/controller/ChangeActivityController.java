package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangeActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @PostMapping("/activity/change")
    protected String saveOrUpdateActivity(@ModelAttribute("activity") Activity activity, BindingResult result) {
        if (result.hasErrors()) {
            return "activityData";
        } else {
            activityRepository.save(activity);
            return "redirect:/activity/all";
        }
    }
}
