package com.wemakeitwork.allenvooreen.model;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailInvitation {
    private String message;

    private String email;

    private MailSender mailSender;

    private SimpleMailMessage simpleMailMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

