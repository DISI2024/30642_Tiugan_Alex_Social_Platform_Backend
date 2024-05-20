package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private ResetPasswordService resetPasswordService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, ResetPasswordService resetPasswordService) {
        this.javaMailSender = javaMailSender;
        this.resetPasswordService = resetPasswordService;
    }

    public static String generateRandomString() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int LENGTH = 6;
        Random random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    public void sendResetPasswordEmail(User user, String receiverEmail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress("mailtrap@demomailtrap.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, receiverEmail);
        message.setSubject("Reset your password");

        String token = generateRandomString(); //a random token
        resetPasswordService.saveInResetPasswordTable(user.getUsername(), token);

        String htmlContent =  "Dear " + user.getUsername() + ",<br><br>"
                + "We have received a request to reset your password. This is your code for resetting your password: <br><br>"
                + token;
        message.setContent(htmlContent, "text/html; charset=utf-8");

        javaMailSender.send(message);
    }
}
