package com.wemakeitwork.allenvooreen.controller;

import com.wemakeitwork.allenvooreen.model.Member;
//import com.wemakeitwork.allenvooreen.service.NotificationService;
import com.wemakeitwork.allenvooreen.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/signup")
    public String signup(){
        return "Please sign up for our service.";
    }


    @RequestMapping("/signup-success")
    public String signupSuccess() {

        /*// create user
        Member member = new Member();
        member.setMemberName("Anna");
        member.setEmail("ambrand5@hotmail.com");

        // send a notification
        try {
            notificationService.sendNotification(member);
        }catch (MailException e) {
            logger.info("Error Sending Email: " + e.getMessage());
        }

     */
        return "Je bent succesvol geregistreerd. Controleer je email en klik op de link om je aanmelding af te ronden.";
    }
}






