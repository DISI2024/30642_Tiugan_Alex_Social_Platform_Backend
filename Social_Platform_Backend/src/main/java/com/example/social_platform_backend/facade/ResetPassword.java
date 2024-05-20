package com.example.social_platform_backend.facade;

import jakarta.persistence.*;
import utils.Util;

@Entity
@Table(name="reset_password")
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="username", unique = true, nullable = false)
    String username;

    @Column(name="reset_token", unique = true, nullable = true)
    String token;

    public Long getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = Util.getLong(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
