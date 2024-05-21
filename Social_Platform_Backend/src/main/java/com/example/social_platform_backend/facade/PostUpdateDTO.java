package com.example.social_platform_backend.facade;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateDTO {

    private Long id;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String photoUrl;

    private boolean blocked;
}
