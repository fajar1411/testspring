package com.example.testSpring.service.imp;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.testSpring.dto.RequestAmartek;
import com.example.testSpring.handler.CustomResponse;
import com.example.testSpring.model.Amartek;
import com.example.testSpring.model.Role;
import com.example.testSpring.model.TrAmartek;
import com.example.testSpring.model.User;
import com.example.testSpring.repository.AmartekRepository;
import com.example.testSpring.repository.RoleRepository;
import com.example.testSpring.repository.TrAmartekRepository;
import com.example.testSpring.repository.UserRepository;
import com.example.testSpring.service.FajarService;
import com.example.testSpring.service.SendEmailService;

import net.bytebuddy.utility.RandomString;

@Service
public class FajarImplementService implements FajarService {
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AmartekRepository amartekRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrAmartekRepository trAmartekRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired 
    private SendEmailService sendEmailService;

    @Override
    public ResponseEntity<Object> codeKata(String kata) {
        int counta = 0;
        int counti = 0;
        int countu = 0;
        int counte = 0;
        int counto = 0;
        int countnonvokal = 0;

        Map<String, Integer> response = new HashMap<>();
        for (int i = 0; i < kata.length(); i++) {
            // indonesia
            if (kata.charAt(i) == 'a') {

                counta++;
                response.put("a", counta);

            } else if (kata.charAt(i) == 'i') {
                counti++;
                response.put("i", counti);

            } else if (kata.charAt(i) == 'u') {
                countu++;
                response.put("u", countu);

            } else if (kata.charAt(i) == 'e') {
                counte++;
                response.put("e", counte);

            } else if (kata.charAt(i) == 'o') {
                counto++;
                response.put("o", counto);

            } else {
                countnonvokal++;
                response.put("nonvokal", countnonvokal);
            }

        }
        return CustomResponse.generate(HttpStatus.OK, "success", response);
    }

    @Override
    public ResponseEntity<Object> createdData(RequestAmartek requestAmartek, String siteUrl)throws MessagingException, UnsupportedEncodingException {

        if (requestAmartek.getName().equals("") || requestAmartek.getName() == null
                || requestAmartek.getTanggalLahir().isEmpty() || requestAmartek.getTanggalLahir() == null
                || requestAmartek.getEmail().isEmpty() || requestAmartek.getPassword().isEmpty()
                || requestAmartek.getPassword() == null
                || requestAmartek.getEmail() == null) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Cek Your Input");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");// untuk memformat atau
                                                                                         // mem-parse string tanggal
        // variable date time tipedatanya adalah sebuah object LocalDateTime yang
        // valuenya berdasarkan konversi string berdasarkan format yang telah ditentukan
        // oleh formatter
        LocalDateTime dateTime = LocalDateTime.parse(requestAmartek.getTanggalLahir(), formatter);
        Amartek amartek = new Amartek();
        amartek.setName(requestAmartek.getName());
        amartek.setTanggalLahir(dateTime);
        amartek.setUmur(requestAmartek.getUmur());
        amartek.setEmail(requestAmartek.getEmail());

        amartekRepository.save(amartek);

        TrAmartek trAmartek = new TrAmartek();
        boolean result = amartekRepository.findById(amartek.getId()).isPresent();
        // is present untuk mengecek apa datanya atau tidak

        if (result) {
            String randomCode = RandomString.make(64);
            User user = new User();
            Role role = roleRepository.findRole(requestAmartek.getRole());
            user.setId(amartek.getId());
            user.setPassword(requestAmartek.getPassword());
            user.setStatus("nonAktif");
            user.setVerificationCode(randomCode);
            userRepository.save(user);
            requestAmartek.setVerificationCode(randomCode);
            trAmartek.setRole(role);
            trAmartek.setUser(user);
            trAmartekRepository.save(trAmartek);
            Boolean resultUser = userRepository.findById(amartek.getId()).isPresent();
            if (resultUser) {
              sendEmailService.sendVerificationEmail(requestAmartek, siteUrl);
             
           
                return CustomResponse.generate(HttpStatus.CREATED, "register Successfully Added");
            }

        } else {
            userRepository.deleteById(amartek.getId());
            trAmartekRepository.deleteById(amartek.getId());
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Error Adding Data");
        }

        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Register Failed");

    }

    @Override
    public ResponseEntity<Object> FindAll() {
        return CustomResponse.generate(HttpStatus.OK, "success view data", amartekRepository.findAll());
    }

    
}
