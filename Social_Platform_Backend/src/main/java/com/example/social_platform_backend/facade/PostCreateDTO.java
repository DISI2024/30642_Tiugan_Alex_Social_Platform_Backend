package com.example.social_platform_backend.facade;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateDTO {

    private Long userID;
    private String photoURL;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;


}
