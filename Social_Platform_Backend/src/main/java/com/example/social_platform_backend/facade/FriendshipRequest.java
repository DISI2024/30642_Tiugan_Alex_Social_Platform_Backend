package com.example.social_platform_backend.facade;

import com.example.social_platform_backend.facade.status.FriendshipRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name="friendship_request")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendshipRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FriendshipRequestStatus status;

    private LocalDateTime sentAt;
}
