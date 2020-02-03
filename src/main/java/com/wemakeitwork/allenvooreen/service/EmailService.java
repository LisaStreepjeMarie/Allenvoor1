package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.controller.ErrorsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private MailSender mailSender;

    private SimpleMailMessage mailMessage;
    @Autowired
    private ErrorsController errorsController;

    public void sendEmail(String email, String subject, String message){

        SimpleMailMessage invitation = new SimpleMailMessage(mailMessage);
        invitation.setTo(email);
        invitation.setSubject(subject);
        invitation.setText(message);
        try {
            mailSender.send(invitation);
        }
        catch (MailException exception) {
            errorsController.getErrorPath();
        }
    }
}




