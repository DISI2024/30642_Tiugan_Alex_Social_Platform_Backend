package com.example.social_platform_backend.Facade.Convertor;

import com.example.social_platform_backend.Facade.RegisterDto;
import com.example.social_platform_backend.Facade.RegisterResponseDto;
import com.example.social_platform_backend.Facade.User;
import com.example.social_platform_backend.Facade.UserDTO;

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

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
