package com.wsb.wsbfinalproject2022.mails;

import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

    private final PersonRepository personRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wsbmarcinzadrzynski@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}