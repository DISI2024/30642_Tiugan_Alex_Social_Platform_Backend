package com.example.social_platform_backend.facade;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String jwt;
    private User user;

    public LoginResponseDto(String jwtToken, User user) {
        this.jwt = jwtToken;
        this.user = user;
    }
}
