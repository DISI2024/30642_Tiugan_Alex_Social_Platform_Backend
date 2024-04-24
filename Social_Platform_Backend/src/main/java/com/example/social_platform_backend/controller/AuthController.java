package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.exceptions.LoginException;
import com.example.social_platform_backend.exceptions.RegisterException;
import com.example.social_platform_backend.facade.LoginDto;
import com.example.social_platform_backend.facade.RegisterDto;
import com.example.social_platform_backend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationService authenticationService;

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





}
