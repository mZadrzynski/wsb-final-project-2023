package com.wsb.wsbfinalproject2022.mails;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mails")
public class MailController {

    @GetMapping
    String showForm() {
        return "Mails/mail";
    }

    @PostMapping
    String sendMail() {


        return "Mail/mail";
    }

}
