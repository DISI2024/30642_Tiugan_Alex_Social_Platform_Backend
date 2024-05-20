package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.User;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

        message.setFrom(new InternetAddress("mihaligabriel75@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, receiverEmail);
        message.setSubject("Reset your password");

        String token = generateRandomString(); //a random token
        resetPasswordService.saveInResetPasswordTable(user.getUsername(), token);

        String htmlContent =  "Dear " + user.getUsername() + ",<br><br>"
                + "We have received a request to reset your password. This is your code for resetting your password: <br><br>"
                + token + "\">Reset Password</a>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        javaMailSender.send(message);
    }
    //sends the reset password email for the respective recipient
//    public void sendResetPasswordEmail(User user, String receiverEmail) {
//        try {
//
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(receiverEmail);
//
//            helper.setText("");
//
//            // Set Subject: header field
//            message.setSubject("Testing Subject");
//
//            // Create the message part
//            BodyPart messageBodyPart = new MimeBodyPart();
//
//            // Now set the actual message
//            messageBodyPart.setText("");
//
//            // Create a multipar message
//            Multipart multipart = new MimeMultipart();
//
//            // Set text message part
//            multipart.addBodyPart(messageBodyPart);
//
//            // Part two is attachment
//            messageBodyPart = new MimeBodyPart();
//
//
//            String msg = "Dear " + user.getUsername() + ",<br><br>"
//                    + "We have received a request to reset your password. This is your code for resetting your password: <br><br>"
//                    + token + "\">Reset Password</a>";
//
//            messageBodyPart.setContent(msg, "text/html; charset=utf-8");
//            multipart.addBodyPart(messageBodyPart);
//
//            // Send the complete message parts
//            message.setContent(multipart);
//
//            // Send message
//            javaMailSender.send(message);
//
//        }
//        catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
