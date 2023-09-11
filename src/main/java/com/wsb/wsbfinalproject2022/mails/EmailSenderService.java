package com.wsb.wsbfinalproject2022.mails;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
//
//    private final PersonRepository personRepository;
//
//    @Autowired
//    private static JavaMailSender mailSender;
//
//    public EmailSenderService(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }
//    Person person;
//    String toEmail = person.getEmail();
//

//    public static void sendEmail(String toEmail, String body, String subject) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("wsbmarcinzadrzynski@gmail.com");
//        message.setTo(toEmail);
//        message.setText(body);
//        message.setSubject(subject);
//
//        mailSender.send(message);
//        System.out.println("mail sent succes");
//    }


}