package com.example.social_platform_backend.facade;

import lombok.Getter;

@Getter
public class UserDTO {
    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private String role;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
