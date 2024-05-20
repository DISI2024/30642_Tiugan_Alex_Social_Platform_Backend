package com.example.social_platform_backend.controller;

import com.example.social_platform_backend.Facade.Convertor.UserConvertor;
import com.example.social_platform_backend.Facade.User;
import com.example.social_platform_backend.Facade.UserDTO;
import com.example.social_platform_backend.Service.UserService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.social_platform_backend.facade.ResetPasswordDTO;
import com.example.social_platform_backend.facade.User;
import com.example.social_platform_backend.service.EmailService;
import com.example.social_platform_backend.service.ResetPasswordService;
import com.example.social_platform_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public User postUser(@RequestBody User user){
        return userService.postUser(user);
    }

    @PostMapping("/user/{username}/add-friend/{friend}")
    public ResponseEntity<Object> addFriend(@PathVariable String username, @PathVariable String friend) {
        try {
            User user = userService.getUserByUsername(username);
            User friendEntity = userService.getUserByUsername(friend);

            if(user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if(friend == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Friend not found");
            }

            userService.addFriend(user, friendEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Friend added succesfully");

        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PutMapping("/user")
    public User putUser(@RequestBody User user){
        return userService.putUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @DeleteMapping("/user/{username}/add-friend/{friend}")
    public ResponseEntity<Object> removeFriend(@PathVariable String username, @PathVariable String friend) {
        try {
            User user = userService.getUserByUsername(username);
            User friendEntity = userService.getUserByUsername(friend);

            if(user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if(friend == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Friend not found");
            }

            userService.removeFriend(user, friendEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Friend removed succesfully");

        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @GetMapping("/user/friends/{username}")
    public ResponseEntity<Object> getUserFriendList(@PathVariable String username) {
        try {
            Set<User> friendsList = userService.getFriendsListByUsername(username);
            List<UserDTO> friendsListDTO = friendsList.stream()
                                                    .map(UserConvertor::toUserDTO)
                                                    .collect(Collectors.toList());

            if(friendsList == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(friendsListDTO);

        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }
}
