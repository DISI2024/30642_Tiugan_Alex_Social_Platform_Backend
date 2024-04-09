package com.example.social_platform_backend.Repository;

import com.example.social_platform_backend.Facade.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
