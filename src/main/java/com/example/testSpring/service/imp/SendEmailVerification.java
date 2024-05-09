package com.example.testSpring.service.imp;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.testSpring.dto.RequestAmartek;
import com.example.testSpring.service.SendEmailService;

@Service
public class SendEmailVerification implements SendEmailService{
  @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void sendVerificationEmail(RequestAmartek requestAmartek, String siteUrl) throws MessagingException,UnsupportedEncodingException {
      String toAddress = requestAmartek.getEmail();
      String fromAddress = "frizky861@gmail.com";
      // String senderName = "wakwaw";
      String subject = "Please verify your registration";
      String content = "Dear [[name]],<br>"
              + "Please click the link below to verify your registration:<br>"
              + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
              + "Thank you,<br>"
              + "Your company name.";
       
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
       
      helper.setFrom(fromAddress);
      helper.setTo(toAddress);
      helper.setSubject(subject);
       
      content = content.replace("[[name]]", requestAmartek.getName());
      String verifyURL = siteUrl + "/verify?code=" + requestAmartek.getVerificationCode();
       
      content = content.replace("[[URL]]", verifyURL);
       
      helper.setText(content, true);
       
      mailSender.send(message);
       
      }
}
