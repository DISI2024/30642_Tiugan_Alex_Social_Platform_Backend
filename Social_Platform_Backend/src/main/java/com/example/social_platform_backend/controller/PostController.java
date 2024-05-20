package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.facade.Post;
import com.example.social_platform_backend.facade.PostCreateDTO;
import com.example.social_platform_backend.facade.PostUpdateDTO;
import com.example.social_platform_backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<Post>> getFeed(@PathVariable Long userId) {
        List<Post> feed = postService.getFeed(userId);
        return ResponseEntity.ok(feed);
    }

    @PostMapping("/add")
    public ResponseEntity<Post> addPost(@RequestBody PostCreateDTO postCreateDTO) {
        Post newPost = postService.addPost(postCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable Long userId) {
        List<Post> userPosts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(userPosts);
    }

    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody PostUpdateDTO updateDTO) {
        Post updatedPost = postService.updatePost(updateDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Deleted successfully");
    }
}