package com.example.trelloclone.controllers;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> index() {
        List<AppUser> allAppUsers = appUserService.getAllUsers();
        return ResponseEntity.ok().body(allAppUsers);
    }

    @GetMapping(path = "{username}")
    public ResponseEntity<AppUser> findByUsername(@PathVariable("username") String username) {
        AppUser appUser = appUserService.getUserByUsername(username);
        return ResponseEntity.ok().body(appUser);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/register")
                .toUriString()
        );
        AppUser newAppUser = appUserService.createUser(appUser);
        return ResponseEntity.created(uri).body(newAppUser);
    }

    @GetMapping("/{username}/boards")
    public ResponseEntity<List<Board>> getUsersBoards(@PathVariable String username) {
        List<Board> boards = appUserService.getUsersBoards(username);
        return ResponseEntity.ok().body(boards);
    }

}
