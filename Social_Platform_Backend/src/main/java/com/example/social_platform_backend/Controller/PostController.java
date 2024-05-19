package com.example.social_platform_backend.Controller;

import com.example.social_platform_backend.Facade.Post;
import com.example.social_platform_backend.Facade.PostCreateDTO;
import com.example.social_platform_backend.Facade.PostUpdateDTO;
import com.example.social_platform_backend.Service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/post/{username}")
    public List<Post> getUsersPosts(@PathVariable String username){
        return postService.getUsersPosts(username);
    }

    @PostMapping("/post")
    public Post postPost(@RequestBody PostCreateDTO post){
        return postService.postPost(post);
    }

    @PutMapping("/post")
    public Post putPost(@RequestBody PostUpdateDTO post){
        return postService.putPost(post);
    }

    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }

}
