package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.FriendshipRequest;
import com.example.social_platform_backend.facade.FriendshipRequestDTO;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.facade.convertor.FriendshipRequestConvertor;
import com.example.social_platform_backend.facade.status.FriendshipRequestStatus;
import com.example.social_platform_backend.repository.FriendshipRequestRepository;
import com.example.social_platform_backend.repository.UserRepository;
import jakarta.validation.constraints.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipRequestService {

    private final FriendshipRequestRepository friendshipRequestRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(FriendshipRequestService.class);

    public FriendshipRequestService(FriendshipRequestRepository friendshipRequestRepository, UserRepository userRepository, UserService userService) {
        this.friendshipRequestRepository = friendshipRequestRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<FriendshipRequestDTO> findAllPendingByReceiverUsername(String username) {

        List<FriendshipRequestDTO> requests = friendshipRequestRepository.findAllPendingByReceiverUsername(username)
                .stream()
                .map(FriendshipRequestConvertor::entityToDTO)
                .toList();

        return requests;
    }


    public Optional<FriendshipRequestDTO> addFriendshipRequest(String senderUsername, String receiverUsername) {
        Optional<User> sender = userRepository.findByUsername(senderUsername);

        if (sender.isEmpty()) {
            logger.error("Sender with username {} not found", senderUsername);
            return Optional.empty();
        }

        Optional<User> receiver = userRepository.findByUsername(receiverUsername);

        if (receiver.isEmpty()) {
            logger.error("Receiver with username {} not found", receiverUsername);
            return Optional.empty();
        }

        Optional<FriendshipRequest> friendshipRequest = friendshipRequestRepository
                .findBySenderIdAndReceiverId(sender.get().getId(), receiver.get().getId());

        if (friendshipRequest.isPresent()) {
            logger.error("Friendship request already exists");
            return Optional.empty();
        }

        FriendshipRequest newFriendshipRequest = new FriendshipRequest();
        newFriendshipRequest.setSender(sender.get());
        newFriendshipRequest.setReceiver(receiver.get());
        newFriendshipRequest.setStatus(FriendshipRequestStatus.PENDING);
        newFriendshipRequest.setSentAt(LocalDateTime.now());

        FriendshipRequest saved = friendshipRequestRepository.save(newFriendshipRequest);
        Optional<FriendshipRequestDTO> savedFriendshipRequestDto = Optional.of(FriendshipRequestConvertor.entityToDTO(saved));

        return savedFriendshipRequestDto;
    }


    public Optional<FriendshipRequestDTO> confirmFriendshipRequest(String receiverUsername, String senderUsername) {
        return changeStatusFriendshipRequest(receiverUsername, senderUsername, FriendshipRequestStatus.ACCEPTED);
    }

    public Optional<FriendshipRequestDTO> rejectFriendshipRequest(String receiverUsername, String senderUsername) {
        return changeStatusFriendshipRequest(receiverUsername, senderUsername, FriendshipRequestStatus.REJECTED);
    }

    private Optional<FriendshipRequestDTO> changeStatusFriendshipRequest(String receiverUsername, String senderUsername, FriendshipRequestStatus status) {
        Optional<User> sender = userRepository.findByUsername(senderUsername);

        if (sender.isEmpty()) {
            logger.error("Sender with username {} not found", senderUsername);
            return Optional.empty();
        }

        Optional<User> receiver = userRepository.findByUsername(receiverUsername);

        if (receiver.isEmpty()) {
            logger.error("Receiver with username {} not found", receiverUsername);
            return Optional.empty();
        }

        Optional<FriendshipRequest> friendshipRequest = friendshipRequestRepository
                .findBySenderIdAndReceiverId(sender.get().getId(), receiver.get().getId());

        if (friendshipRequest.isEmpty()) {
            logger.error("Friendship request not found");
            return Optional.empty();
        }

        FriendshipRequest updatedFriendshipRequest = friendshipRequest.get();
        updatedFriendshipRequest.setStatus(status);
        FriendshipRequest updated = friendshipRequestRepository.save(updatedFriendshipRequest);

        if (updated == null) {
            logger.error("Friendship request not updated");
            return Optional.empty();
        }

        Optional<FriendshipRequestDTO> updatedFriendshipRequestDto = Optional.of(FriendshipRequestConvertor.entityToDTO(updated));

        if (status == FriendshipRequestStatus.ACCEPTED) {
            userService.addFriend(sender.get(), receiver.get());
        }

        return updatedFriendshipRequestDto;
    }


}
