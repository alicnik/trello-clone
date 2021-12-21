package com.example.trelloclone.controllers;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.Comment;
import com.example.trelloclone.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cards")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{id}")
    public Optional<Comment> getSingleComment(@PathVariable String id) {
        return commentService.getSingleComment(id);
    }

    @PostMapping("/{cardId}/comments")
    public Board createNewComment(Principal principal, @PathVariable String cardId, @RequestBody Comment comment) {
        return commentService.createNewComment(principal.getName(), cardId, comment);
    }

    @PutMapping("/comments/{commentId}")
    public Board updateComment(Principal principal, @PathVariable String commentId, @RequestBody Comment updatedComment) {
        return commentService.updateComment(principal.getName(), commentId, updatedComment);
    }

    @DeleteMapping("/comments/{commentId}")
    public Board deleteComment(Principal principal, @PathVariable String commentId) {
        return commentService.deleteComment(principal.getName(), commentId);
    }
}
