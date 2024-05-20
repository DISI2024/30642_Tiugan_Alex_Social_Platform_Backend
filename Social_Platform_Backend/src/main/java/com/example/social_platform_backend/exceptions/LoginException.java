package com.example.social_platform_backend.exceptions;

public class LoginException extends RuntimeException{
    public LoginException(String msg){
        super(msg);
    }
}
