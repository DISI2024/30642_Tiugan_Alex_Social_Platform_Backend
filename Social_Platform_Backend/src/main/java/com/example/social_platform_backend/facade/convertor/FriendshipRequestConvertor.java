package com.example.social_platform_backend.facade.convertor;

import com.example.social_platform_backend.facade.FriendshipRequest;
import com.example.social_platform_backend.facade.FriendshipRequestDTO;

public class FriendshipRequestConvertor {

    public static FriendshipRequestDTO entityToDTO(FriendshipRequest friendshipRequest) {
        FriendshipRequestDTO friendshipRequestDTO = new FriendshipRequestDTO();
        friendshipRequestDTO.setId(friendshipRequest.getId());
        friendshipRequestDTO.setSender(UserConvertor.toUserDTO(friendshipRequest.getSender()));
        friendshipRequestDTO.setReceiver(UserConvertor.toUserDTO(friendshipRequest.getReceiver()));
        friendshipRequestDTO.setStatus(friendshipRequest.getStatus());
        friendshipRequestDTO.setSentAt(friendshipRequest.getSentAt());
        return friendshipRequestDTO;
    }

}
