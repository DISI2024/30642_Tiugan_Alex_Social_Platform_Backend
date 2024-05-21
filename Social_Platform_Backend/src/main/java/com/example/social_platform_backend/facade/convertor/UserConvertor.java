package com.example.social_platform_backend.facade.convertor;

import com.example.social_platform_backend.facade.RegisterDto;
import com.example.social_platform_backend.facade.RegisterResponseDto;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.facade.UserDTO;

public class UserConvertor {

    public RegisterDto toRegisterDTO(User user) {
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

    public static User toUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getFirstname(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                userDTO.getRole(),
                userDTO.getPhotoUrl()
        );

        return user;
    }
}
