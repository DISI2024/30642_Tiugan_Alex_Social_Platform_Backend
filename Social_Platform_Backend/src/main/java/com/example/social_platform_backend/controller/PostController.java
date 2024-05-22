package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.facade.Post;
import com.example.social_platform_backend.facade.PostCreateDTO;
import com.example.social_platform_backend.facade.PostDTO;
import com.example.social_platform_backend.facade.PostUpdateDTO;
import com.example.social_platform_backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("*")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @GetMapping("/feed/{username}")
    public ResponseEntity<List<PostDTO>> getFeed(@PathVariable String username) {
        List<PostDTO> feed = postService.getFeed(username);
        return ResponseEntity.ok(feed);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> feed = postService.getAllPosts();
        return ResponseEntity.ok(feed);
    }

    @PostMapping()
    public ResponseEntity<Post> addPost(@RequestBody PostCreateDTO postCreateDTO) {
        Post newPost = postService.addPost(postCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PostMapping("/block/id/{id}")
    public ResponseEntity<String> blockPost(@PathVariable Long id) {
        postService.blockPost(id);
        return ResponseEntity.ok("Post blocked successfully");
    }

    @PostMapping("/unblock/id/{id}")
    public ResponseEntity<String> unblockPost(@PathVariable Long id) {
        postService.unblockPost(id);
        return ResponseEntity.ok("Post unblocked successfully");
    }

    @GetMapping("/id/{postId}")
    public ResponseEntity<Post> getPostsById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable String username) {
        List<PostDTO> userPosts = postService.getPostsByUser(username);
        return ResponseEntity.ok(userPosts);
    }

    @PutMapping()
    public ResponseEntity<Post> updatePost(@RequestBody PostUpdateDTO updateDTO) {
        Post updatedPost = postService.updatePost(updateDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/id/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Deleted successfully");
    }
}
