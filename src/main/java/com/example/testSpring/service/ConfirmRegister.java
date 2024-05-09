package com.example.testSpring.service;

import org.springframework.http.ResponseEntity;

public interface ConfirmRegister {
    public ResponseEntity<Object> confirmToken(String Token);
}
