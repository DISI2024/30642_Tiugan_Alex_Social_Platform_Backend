package com.example.social_platform_backend.service.initializer;

import com.example.social_platform_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    @Autowired
    public AdminInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.createAdmin();
        logger.info("Admin created");
    }
}
