package com.prs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingConfirmation(String recipientEmail, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom("easy.home.properties.rental@gmail.com");
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Booking Status Update");
        mailMessage.setText(message);
//        System.out.println(mailSender.);
        mailSender.send(mailMessage);
    }
}
