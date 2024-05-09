package com.example.testSpring.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;

import com.example.testSpring.dto.RequestAmartek;

public interface FajarService {
    public ResponseEntity<Object> createdData(RequestAmartek requestAmartek, String siteUrl)throws MessagingException, UnsupportedEncodingException;

    public ResponseEntity<Object> FindAll();

    public ResponseEntity<Object> codeKata(String kata);
}

