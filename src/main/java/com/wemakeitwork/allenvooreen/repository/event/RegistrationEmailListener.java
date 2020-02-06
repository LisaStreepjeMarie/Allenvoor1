package com.wemakeitwork.allenvooreen.repository.event;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.service.MemberServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    @Autowired
    private MemberServiceInterface userService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private MessageSource messages;

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {
        Member member = event.getMember();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(member,token);

        String recipient = member.getEmail();
        String subject = "Registration Confirmation";
        String url
                = event.getAppUrl() + "/confirmRegistration?token=" + token;
        //String message = messages.getMessage("message.registrationSuccessConfirmationLink", null, event.getLocale());

        String message = "Bedankt voor je aanmelding. Je bent nog 1 stap van registratie vandaan. Klik op deze link om je profiel te bevestigen. ";


        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject("Welkom bij Allen voor Een");
        email.setText( message + "\nhttp://localhost:8080" + url);
        System.out.println(url);
        mailSender.send(email);
    }
}
