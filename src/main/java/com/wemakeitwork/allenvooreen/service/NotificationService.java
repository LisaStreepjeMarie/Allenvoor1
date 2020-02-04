package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
 public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(Member member) throws MailException {
        // send email
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(member.getEmail());
        mail.setFrom("allenvooreenapplicatie@gmail.com");
        mail.setSubject("Welkom bij Allen voor een");
        mail.setText("Bedankt voor je aanmelding. Je bent nog 1 stap van registratie vandaan. Klik op deze link om je profiel te bevestigen." );

        javaMailSender.send(mail);
    }
}


