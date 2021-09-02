package com.example.trelloclone.controllers;

import com.example.trelloclone.models.Card;
import com.example.trelloclone.models.Comment;
import com.example.trelloclone.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cards/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public Optional<Comment> getSingleComment(@PathVariable Long id) {
        return commentService.getSingleComment(id);
    }

    @PostMapping
    public Comment createNewComment(@RequestBody Comment comment) {
        return commentService.createNewComment(comment);
    }
}
