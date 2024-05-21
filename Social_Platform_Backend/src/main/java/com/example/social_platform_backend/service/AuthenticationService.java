package com.example.social_platform_backend.service;

import com.example.social_platform_backend.config.JwtService;
import com.example.social_platform_backend.exceptions.LoginException;
import com.example.social_platform_backend.exceptions.RegisterException;
import com.example.social_platform_backend.facade.*;
import com.example.social_platform_backend.facade.convertor.UserConvertor;
import com.example.social_platform_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserConvertor userConvertor;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponseDto register(RegisterDto registerDto) throws RegisterException {
        try {
            User user = new User();

            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setFirstname(registerDto.getFirstname());
            user.setLastname(registerDto.getLastname());
            user.setEmail(registerDto.getEmail());
            user.setRole("USER");

            User savedUser = userRepository.save(user);

            return userConvertor.toRegisterResponseDTO(savedUser);
        }
        catch(RegisterException e) {
            //have to change what I send to the front end in the case of 500 server error
            throw new RegisterException("Register failed");
        }
    }

    public LoginResponseDto login(LoginDto loginDto) throws LoginException{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        try {
            User user = userRepository.findUserByUsername(loginDto.getUsername()).get();
            UserDTO userDTO = userConvertor.toUserDTO(userRepository.findUserByUsername(loginDto.getUsername()).get());
            if (user == null)
                throw new LoginException("User " + loginDto.getUsername() + " not found!");

            var jwtToken = jwtService.generateToken(user);
            return new LoginResponseDto(
                    jwtToken,
                    userDTO
            );
        }
        catch(LoginException e) {
            //have to change what I send to the front end in the case of 500 server error
            throw new LoginException(e.getMessage());
        }
    }
}
