package com.example.social_platform_backend.repository;

import com.example.social_platform_backend.facade.Post;
import com.example.social_platform_backend.facade.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
}
