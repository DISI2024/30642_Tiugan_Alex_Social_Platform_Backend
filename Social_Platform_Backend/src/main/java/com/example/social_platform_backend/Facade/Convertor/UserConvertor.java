package com.example.social_platform_backend.Facade.Convertor;

import com.example.social_platform_backend.Facade.RegisterDto;
import com.example.social_platform_backend.Facade.RegisterResponseDto;
import com.example.social_platform_backend.Facade.User;

public class UserConvertor {

    public RegisterDto toRegisterDTO(User user){
        RegisterDto registerDto = new RegisterDto(
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
        return registerDto;
    }

    public RegisterResponseDto toRegisterResponseDTO(User user) {
        RegisterResponseDto registerResponseDto = new RegisterResponseDto(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
        return registerResponseDto;
    }
}
