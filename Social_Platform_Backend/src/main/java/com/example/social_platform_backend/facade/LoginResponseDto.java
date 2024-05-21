package com.example.social_platform_backend.facade;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String jwt;
    private UserDTO user;

    public LoginResponseDto(String jwtToken, UserDTO userDTO) {
        this.jwt = jwtToken;
        this.user = userDTO;
    }
}
