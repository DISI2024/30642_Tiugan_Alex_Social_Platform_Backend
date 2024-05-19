package com.example.social_platform_backend.Repository;

import com.example.social_platform_backend.Facade.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
