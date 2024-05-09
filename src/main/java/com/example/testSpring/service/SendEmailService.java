package com.example.testSpring.service;



import javax.mail.MessagingException;

import com.example.testSpring.dto.RequestAmartek;

public interface SendEmailService {
    public void sendVerificationEmail(RequestAmartek requestAmartek, String buildMail, String subject) throws MessagingException;
    public void confirmRegister(RequestAmartek requestAmartek, String token) throws MessagingException;
}
