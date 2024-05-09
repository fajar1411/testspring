package com.example.testSpring.service.imp;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.testSpring.dto.RequestAmartek;
import com.example.testSpring.service.SendEmailService;

@Service
public class SendEmailVerification implements SendEmailService {
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private TemplateEngine templateEngine;
  @Value("${application.frontend.origin-url}")
  private String frontendOriginUrl;

  @Async
  @Override
  public void confirmRegister(RequestAmartek requestAmartek, String token) throws MessagingException {
    String link = String.format("%s/api/auth/confirm?token=%s", frontendOriginUrl, token);
    String subject = "Confirm your account";

    //ganti isi html
    Context context = new Context();
    context.setVariable("name", requestAmartek.getName());
    context.setVariable("link", link);

    String buildMail = templateEngine.process("auth/confirm-email.html", context);
    sendVerificationEmail(requestAmartek, buildMail, subject);
  }

  @Override
  public void sendVerificationEmail(RequestAmartek requestAmartek, String buildMail, String subject)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    helper.setText(buildMail, true);
    helper.setTo(requestAmartek.getEmail());
    helper.setSubject(subject);
    helper.setFrom("frizky861@gmail.com");
    mailSender.send(mimeMessage);

  }

}
