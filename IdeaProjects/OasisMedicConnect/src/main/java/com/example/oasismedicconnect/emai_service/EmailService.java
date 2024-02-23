package com.example.oasismedicconnect.emai_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    public void sendTokenEmail(String token, String emailAddress){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Email Verification");
        mailMessage.setText("kindly click the token to verify your account "+ token);

    emailSender.send(mailMessage);
    }
}
