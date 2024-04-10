package com.example.social_platform_backend.Service;

import com.example.social_platform_backend.Facade.User;
import com.example.social_platform_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
