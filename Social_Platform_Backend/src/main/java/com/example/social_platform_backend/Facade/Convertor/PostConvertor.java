package com.example.social_platform_backend.Facade.Convertor;

import com.example.social_platform_backend.Facade.*;

public class PostConvertor {

    public PostUpdateDTO toUpdateDTO(Post post){
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO(
            post.getId(),
            post.getDescription(),
            post.getPhotoURL()
        );
        return postUpdateDTO;
    }

    public PostCreateDTO toCreateDTO(Post post) {
        PostCreateDTO postCreateDTO = new PostCreateDTO(
                post.getPhotoURL(),
                post.getDescription(),
                post.getUsername()
        );
        return postCreateDTO;
    }
}
