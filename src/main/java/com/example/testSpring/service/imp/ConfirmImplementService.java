package com.example.testSpring.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.testSpring.handler.CustomResponse;
import com.example.testSpring.model.User;
import com.example.testSpring.repository.UserRepository;
import com.example.testSpring.service.ConfirmRegister;


@Service
public class ConfirmImplementService implements ConfirmRegister{
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> confirmToken(String Token) {
        User user = userRepository.cekToken(Token);

        if (user == null) {
            return CustomResponse.generate(HttpStatus.OK, "akun tidak ada");
        }

        user.setStatus("aktif");
        userRepository.save(user);

        return CustomResponse.generate(HttpStatus.OK, "selamat akun anda sudah aktif");
    }
    
}
