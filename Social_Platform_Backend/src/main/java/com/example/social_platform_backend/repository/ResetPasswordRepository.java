package com.example.social_platform_backend.repository;

import com.example.social_platform_backend.facade.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
    @Query("select rp.username from ResetPassword rp WHERE rp.token = :token")
    String findUsernameByToken(String token);
    void deleteByUsername(String username);
}
