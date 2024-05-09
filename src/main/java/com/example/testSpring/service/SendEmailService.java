package com.example.testSpring.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.example.testSpring.dto.RequestAmartek;

public interface SendEmailService {
    public void sendVerificationEmail(RequestAmartek requestAmartek, String siteUrl) throws MessagingException,UnsupportedEncodingException ;
}
