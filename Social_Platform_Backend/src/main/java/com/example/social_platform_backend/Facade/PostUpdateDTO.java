package com.example.social_platform_backend.Facade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateDTO {

    private Long id;
    private String description;

    private String photoURL;
}
