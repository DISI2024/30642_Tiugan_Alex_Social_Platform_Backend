package com.example.social_platform_backend.Facade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateDTO {

    private String photoURL;

    private String description;

    private String username;
}
