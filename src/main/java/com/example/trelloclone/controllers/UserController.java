package com.example.trelloclone.controllers;

import com.example.trelloclone.models.User;
import com.example.trelloclone.models.UserLogin;
import com.example.trelloclone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> index() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{username}")
    public User findByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLogin body) {
        return userService.loginUser(body);
    }
}
