package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.Post;
import com.example.social_platform_backend.facade.PostCreateDTO;
import com.example.social_platform_backend.facade.PostUpdateDTO;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.repository.PostRepository;
import com.example.social_platform_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getFeed(Long userId) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Set<User> friends = currentUser.getFriends();
        List<Post> allPosts = postRepository.findAll();

        return allPosts.stream()
                .filter(post -> friends.contains(post.getUser()) && !post.isBlocked())
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Post addPost(PostCreateDTO postCreateDTO) {
        User user = userRepository.findById(postCreateDTO.getUserID()).orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setPhotoURL(postCreateDTO.getPhotoURL());
        post.setDescription(postCreateDTO.getDescription());
        post.setBlocked(false);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }
    public List<Post> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Post> usersPosts = postRepository.findByUser(user);
        return usersPosts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Post updatePost(PostUpdateDTO updateDTO) {
        Post post = postRepository.findById(updateDTO.getId()).orElseThrow(() -> new RuntimeException("Post not found"));

        post.setPhotoURL(updateDTO.getPhotoURL());
        post.setDescription(updateDTO.getDescription());
        post.setBlocked(updateDTO.isBlocked());

        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }
}
