package com.wemakeitwork.allenvooreen.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailServiceConfig {

    private static final String SENDER_EMAIL = "allenvooreenapplicatie@gmail.com";

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(props);

        mailSender.setUsername(SENDER_EMAIL);
        mailSender.setPassword("AllenvooreenWachtwoord");
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        return mailSender;
    }

    @Bean
    public SimpleMailMessage defaultMessage() {
        SimpleMailMessage defaultBericht = new SimpleMailMessage();
        defaultBericht.setTo("default@example.com");
        defaultBericht.setFrom(SENDER_EMAIL);
        defaultBericht.setSubject("Default subject");
        defaultBericht.setText("Default text");
        return defaultBericht;
    }
}

