package com.example.social_platform_backend.repository;

import com.example.social_platform_backend.facade.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);

    @Query(
            value = "SELECT * FROM user WHERE role = 'ADMIN'",
            nativeQuery = true
    )
    Optional<User> findUserWithAdminRole();

    void deleteByUsername(String username);
}
