package com.example.oasismedicconnect.emai_service;

import com.example.oasismedicconnect.controller.TokenController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    private JavaMailSender emailSender;
    @Autowired
    private TokenController controller;


    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendTokenEmail( String token, String emailAddress) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Activate your account");
        mailMessage.setText("Please activate your account to begin using our services. " + "\"https://" + token + "\">Click here to activate");
        emailSender.send(mailMessage);
    }
}
