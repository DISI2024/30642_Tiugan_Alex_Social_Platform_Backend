package com.example.social_platform_backend.facade;

import jakarta.validation.constraints.NotBlank;

public class UsernameDTO {
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ResetPasswordDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
