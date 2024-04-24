package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.facade.ResetPasswordDTO;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.facade.UsernameDTO;
import com.example.social_platform_backend.service.EmailService;
import com.example.social_platform_backend.service.ResetPasswordService;
import com.example.social_platform_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ResetPasswordController {

    private UserService userService;
    private EmailService emailService;
    private ResetPasswordService resetPasswordService;
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    @Autowired
    public ResetPasswordController(UserService userService, EmailService emailService, ResetPasswordService resetPasswordService) {
        this.userService = userService;
        this.emailService = emailService;
        this.resetPasswordService = resetPasswordService;
    }


    @GetMapping("/token")
    public RedirectView getUserByToken(@RequestParam("token") String token) {

        String username = resetPasswordService.findUsernameByToken(token);
        User user = userService.getUserByUsername(username);
        String redirectUrl = "http://localhost:4200/resetpassword?username=" + user.getUsername();

        return new RedirectView(redirectUrl);
    }

    @PostMapping("/send-reset-email")
    public ResponseEntity<Object> sendResetPasswordEmail(@RequestBody UsernameDTO usernameDTO) {
        try {
            logger.info("Reseting password...");
            User user = userService.getUserByUsername(usernameDTO.getUsername());
            emailService.sendResetPasswordEmail(user, "mihaligabriel75@gmail.com");

            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Email sent succesfully\"}");
        }
        catch (Exception e) {
            logger.error("Error sending reset password email: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save-reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            logger.info("Reseting password...");

            User user = userService.getUserByUsername(resetPasswordDTO.getUsername());
            user.setPassword(resetPasswordDTO.getPassword());

            userService.putUser(user);
            resetPasswordService.deleteResetPasswordByUsername(user.getUsername());

            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");

        }
        catch (Exception e) {
            logger.error("Error updating password: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
