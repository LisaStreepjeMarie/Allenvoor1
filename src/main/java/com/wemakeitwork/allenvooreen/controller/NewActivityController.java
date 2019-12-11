package com.wemakeitwork.allenvooreen.controller;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/newActivity")
    String doorsturen(Activity activity){
        activityRepository.save(activity);

        //TODO direct back to the event page it came from
        return "redirect:/...";
    }
}
