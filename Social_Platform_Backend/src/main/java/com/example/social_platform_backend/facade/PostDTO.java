package com.example.social_platform_backend.facade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String photoUrl;
    private String description;

    private boolean blocked;
    private String username;
}
