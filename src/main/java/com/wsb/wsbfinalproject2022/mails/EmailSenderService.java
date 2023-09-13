package com.wsb.wsbfinalproject2022.mails;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {

    @Autowired
   private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String sentTo, Mail mail){
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("wsbmarcinzadrzynski@gmail.com");
                message.setTo(sentTo);
                message.setText(mail.content);
                message.setSubject(mail.subject);
                javaMailSender.send(message);
            } catch (Exception e) {
                System.out.println("Wysyłanie mejla nie powiodło się " + e);
            }


    }
}



