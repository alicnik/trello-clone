package com.example.trelloclone.services;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.models.Comment;
import com.example.trelloclone.repositories.AppUserRepository;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.CardRepository;
import com.example.trelloclone.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            AppUserRepository appUserRepository,
            CardRepository cardRepository,
            BoardRepository boardRepository
    ) {
        this.commentRepository = commentRepository;
        this.appUserRepository = appUserRepository;
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    public Optional<Comment> getSingleComment(String id) {
        return commentRepository.findById(id);
    }

    public Board createNewComment(String username, String cardId ,Comment comment) {
        AppUser author = appUserRepository.findByUsername(username);
        comment.setAuthor(author);
        Card parentCard = cardRepository.getById(cardId);
        comment.setParentCard(parentCard);
        Comment savedComment = commentRepository.save(comment);
        parentCard.getComments().add(savedComment);
        cardRepository.save(parentCard);
        String boardId = parentCard.getBoard().getId();
        return boardRepository.getById(boardId);
    }

    public Board updateComment(String username, String commentId, Comment updatedComment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist");
        }

        Comment existingComment = existingCommentOptional.get();
        if (!Objects.equals(existingComment.getAuthor().getUsername(), username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit someone else's comment");
        }

        existingComment.setBody(updatedComment.getBody());
        commentRepository.save(existingComment);

        return boardRepository.getById(existingComment.getParentCard().getBoard().getId());
    }

    public Board deleteComment(String username, String commentId) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(commentId);
        if (existingCommentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist");
        }

        Comment existingComment = existingCommentOptional.get();
        if (!Objects.equals(existingComment.getAuthor().getUsername(), username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete someone else's comment");
        }

        String boardId = existingComment.getParentCard().getBoard().getId();
        commentRepository.deleteById(commentId);

        return boardRepository.getById(boardId);

    }
}
