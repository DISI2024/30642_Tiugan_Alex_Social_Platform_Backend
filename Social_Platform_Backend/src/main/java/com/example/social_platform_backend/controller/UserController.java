package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.facade.ResetPasswordDTO;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.facade.UsernameDTO;
import com.example.social_platform_backend.service.EmailService;
import com.example.social_platform_backend.service.ResetPasswordService;
import com.example.social_platform_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final ResetPasswordService resetPasswordService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, EmailService emailService, ResetPasswordService resetPasswordService) {
        this.userService = userService;
        this.emailService = emailService;
        this.resetPasswordService = resetPasswordService;
    }

    @GetMapping("/user")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public User postUser(@RequestBody User user){
        return userService.postUser(user);
    }

    @PutMapping("/user")
    public User putUser(@RequestBody User user){
        return userService.putUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping("/user/request-otp/{email}")
    public ResponseEntity<Object> sendResetPasswordEmail(@PathVariable String email) {
        try {
            logger.info("Reseting password...");
            User user = userService.getUserByEmail(email);

            if(user == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

            emailService.sendResetPasswordEmail(user, "mihaligabriel75@gmail.com");

            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Email sent succesfully\"}");
        }
        catch (Exception e) {
            logger.error("Error sending reset password email: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            logger.info("Reseting password...");

            User user = userService.getUserByEmail(resetPasswordDTO.getEmail());
            user.setPassword(resetPasswordDTO.getNewPassword());

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
