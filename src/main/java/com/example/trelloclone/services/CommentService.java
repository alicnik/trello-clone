package com.example.trelloclone.services;

import com.example.trelloclone.models.Comment;
import com.example.trelloclone.repositories.CardRepository;
import com.example.trelloclone.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> getSingleComment(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createNewComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
