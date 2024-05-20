package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.exceptions.LoginException;
import com.example.social_platform_backend.exceptions.RegisterException;
import com.example.social_platform_backend.facade.LoginDto;
import com.example.social_platform_backend.facade.RegisterDto;
import com.example.social_platform_backend.facade.ResetPasswordDTO;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.service.AuthenticationService;
import com.example.social_platform_backend.service.EmailService;
import com.example.social_platform_backend.service.ResetPasswordService;
import com.example.social_platform_backend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final ResetPasswordService resetPasswordService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    public AuthController(UserService userService, AuthenticationService authenticationService, EmailService emailService, PasswordEncoder passwordEncoder, ResetPasswordService resetPasswordService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        try {
            return ResponseEntity.ok(authenticationService.login(loginDto));
        }
        catch (LoginException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        try {
            return ResponseEntity.ok(authenticationService.register(registerDto));
        }
        catch(RegisterException e) {
            //have to change what I send to the front end in the case of 500 server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/request-otp/{email}")
    public ResponseEntity<Object> sendResetPasswordEmail(@PathVariable String email) {
        try {
            logger.info("Reseting password...");
            User user = userService.getUserByEmail(email);

            if(user == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

            emailService.sendResetPasswordEmail(user, user.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Email sent succesfully\"}");
        }
        catch (Exception e) {
            logger.error("Error sending reset password email: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            logger.info("Reseting password...");

            User user = userService.getUserByEmail(resetPasswordDTO.getEmail());
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));

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
