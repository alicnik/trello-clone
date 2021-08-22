package com.example.trelloclone.services;

import com.example.trelloclone.models.User;
import com.example.trelloclone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String loginUser(@RequestBody User.UserLogin body) {
        boolean userExists = userRepository.existsByUsernameAndPassword(
                body.username,
                body.password
        );
        if (userExists) {
            return "Successful";
        } else {
            return "User not found";
        }
    }
}

