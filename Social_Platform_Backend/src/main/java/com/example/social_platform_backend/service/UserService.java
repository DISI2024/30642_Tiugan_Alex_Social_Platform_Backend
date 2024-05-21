package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.facade.UserDTO;
import com.example.social_platform_backend.facade.convertor.UserConvertor;
import com.example.social_platform_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public User getUserByEmail(String email) {
        if(userRepository.findUserByEmail(email).isPresent())
            return userRepository.findUserByEmail(email).get();
        else return null;
    }
    public User postUser(UserDTO userDTO){
        User user = UserConvertor.toUser(userDTO);
        return userRepository.save(user);
    }
    public User getUserByUsername(String username) {
        if(userRepository.findUserByUsername(username).isPresent())
            return userRepository.findUserByUsername(username).get();
        else return null;
    }
    public User putUser(UserDTO userDTO){

        User oldUser = userRepository.findUserByUsername(userDTO.getUsername()).get();
        oldUser.setEmail(userDTO.getEmail());
        oldUser.setFirstname(userDTO.getFirstname());
        oldUser.setLastname(userDTO.getLastname());
        oldUser.setUsername(userDTO.getUsername());
        oldUser.setPhotoUrl(userDTO.getPhotoUrl());
        return userRepository.save(oldUser);
    }

    public User addFriend(User user, User friend) {
        user.addFriend(friend);

        return userRepository.save(user);
    }

    public Set<User> getFriendsListByUsername(String username) {
        if(userRepository.findUserByUsername(username).isPresent())
            return userRepository.findUserByUsername(username).get().getFriends();
        else return null;
    }
    public User removeFriend(User user, User friend) {
        user.removeFriend(friend);

        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getSuggestedFriends(String username) {
        User currentUser = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Set<User> friends = currentUser.getFriends();
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> !user.equals(currentUser) && !friends.contains(user))
                .limit(10)
                .collect(Collectors.toList());
    }
}
