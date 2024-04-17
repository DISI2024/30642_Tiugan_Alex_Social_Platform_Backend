package com.example.social_platform_backend.Controller;

import com.example.social_platform_backend.Facade.LoginDto;
import com.example.social_platform_backend.Facade.RegisterDto;
import com.example.social_platform_backend.Facade.User;
import com.example.social_platform_backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final String testVariable;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            String username = loginDto.getUsername();

            if(userService.getUserByUsername(username) == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username was not found");
            else {
                User loginUserFromDb = userService.getUserByUsername(username);

                if(loginUserFromDb.getPassword().equals(passwordEncoder.encode(loginDto.getPassword())))
                    return ResponseEntity.status(HttpStatus.OK).body("Login succesful");
                else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
            }
        }
        catch(Exception e) {
            //have to change what I send to the front end in the case of 500 server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        try {
            User user = new User();

            user.setUsername(registerDto.getUsername());
            user.setPassword(registerDto.getPassword());
            user.setFirstname(registerDto.getFirstname());
            user.setLastname(registerDto.getLastname());
            user.setEmail(registerDto.getEmail());

            userService.putUser(user);

            //need to send as a message map
            return ResponseEntity.status(HttpStatus.OK).body("User created successfully");

        }
        catch(Exception e) {
            //have to change what I send to the front end in the case of 500 server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
