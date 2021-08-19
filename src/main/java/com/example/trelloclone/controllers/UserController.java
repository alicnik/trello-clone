package com.example.trelloclone.controllers;

import com.example.trelloclone.models.User;
import com.example.trelloclone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
