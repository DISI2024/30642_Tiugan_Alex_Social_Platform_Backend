package com.example.social_platform_backend.facade.convertor;

import com.example.social_platform_backend.facade.Post;
import com.example.social_platform_backend.facade.PostDTO;

public class PostConvertor {

    public PostDTO toPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setBlocked(post.isBlocked());
        postDTO.setId(post.getId());
        postDTO.setDescription(post.getDescription());
        postDTO.setPhotoUrl(post.getPhotoUrl());
        postDTO.setUsername(post.getUser().getUsername());
        return postDTO;
    }
}
