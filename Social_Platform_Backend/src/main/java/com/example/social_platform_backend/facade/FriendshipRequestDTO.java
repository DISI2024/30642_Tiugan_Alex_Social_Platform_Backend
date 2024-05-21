package com.example.social_platform_backend.facade;

import com.example.social_platform_backend.facade.status.FriendshipRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendshipRequestDTO {

    private Long id;

    private UserDTO sender;

    private UserDTO receiver;

    private FriendshipRequestStatus status;

    private LocalDateTime sentAt;
}
