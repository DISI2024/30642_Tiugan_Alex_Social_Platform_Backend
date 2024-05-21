package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.User;
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
    public User postUser(User user){
        return userRepository.save(user);
    }
    public User getUserByUsername(String username) {
        if(userRepository.findUserByUsername(username).isPresent())
            return userRepository.findUserByUsername(username).get();
        else return null;
    }
    public User putUser(User user){
        User oldUser = userRepository.findById(user.getId()).get();
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstname(user.getFirstname());
        oldUser.setLastname(user.getLastname());
        oldUser.setUsername(user.getUsername());
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
                .limit(3)
                .collect(Collectors.toList());
    }
}
