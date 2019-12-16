package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ChangeActivityController {
//    used this activity to test if the jsp is prefilled
//    Activity testActivity = new Activity();

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/activity/update")
    String updateActivity(Model model, Activity activity){
//        testActivity.setActivityName("test");

        model.addAttribute("updateActivityName", activity.getActivityName());
        model.addAttribute("nummer", activity.getActivityCategory());
        return "newActivity";
    }

}
