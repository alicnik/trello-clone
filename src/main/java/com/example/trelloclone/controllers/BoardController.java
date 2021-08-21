package com.example.trelloclone.controllers;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.NewBoard;
import com.example.trelloclone.models.User;
import com.example.trelloclone.services.BoardService;
import com.example.trelloclone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/boards")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping(path = "{boardId}")
    public Optional<Board> getSingleBoard(@PathVariable String boardId) {
        return boardService.getSingleBoard(Long.valueOf(boardId));
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody NewBoard body) {
        User user = userService.getUserByUsername(body.username);
        Board board = boardService.createBoard(body.boardName, user);
        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @DeleteMapping("{boardId}")
    public ResponseEntity deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoard(Long.valueOf(boardId));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
