package com.example.social_platform_backend.repository;

import com.example.social_platform_backend.facade.FriendshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {

    @Query(
            value = "SELECT * FROM friendship_request WHERE receiver_id = (SELECT id FROM user WHERE username = ?1) AND status = 'PENDING' ORDER BY sent_at DESC",
            nativeQuery = true
    )
    List<FriendshipRequest> findAllPendingByReceiverUsername(String username);

    @Query(
            value = "SELECT * FROM friendship_request WHERE sender_id = ?1 AND receiver_id = ?2 AND status = 'PENDING'",
            nativeQuery = true
    )
    Optional<FriendshipRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
