package com.example.social_platform_backend.Service;

import com.example.social_platform_backend.Facade.Post;
import com.example.social_platform_backend.Facade.PostCreateDTO;
import com.example.social_platform_backend.Facade.PostUpdateDTO;
import com.example.social_platform_backend.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getPosts() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream()
                .filter(post -> !post.isBlocked())
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Post postPost(PostCreateDTO postCreateDTO){
        Post savePost = new Post(
                0L,
                postCreateDTO.getUsername(),
                postCreateDTO.getPhotoURL(),
                postCreateDTO.getDescription(),
                false,
                LocalDateTime.now()
        );

        return postRepository.save(savePost);
    }
    public List<Post> getUsersPosts(String username) {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream()
                .filter(post -> post.getUsername().equals(username))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Post putPost(PostUpdateDTO postDTO){
        Post currentPost = postRepository.findById(postDTO.getId()).get();
        currentPost.setDescription(postDTO.getDescription());
        currentPost.setPhotoURL(postDTO.getPhotoURL());
        return postRepository.save(currentPost);
    }

    public void deletePost(Long postID){
        postRepository.deleteById(postID);
    }
}
