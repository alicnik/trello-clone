package com.example.trelloclone.controllers;

import com.example.trelloclone.controllers.helpers.NewBoard;
import com.example.trelloclone.controllers.helpers.URIFactory;
import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.services.AppUserService;
import com.example.trelloclone.services.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/boards")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final AppUserService appUserService;

    @Autowired
    public BoardController(BoardService boardService, AppUserService appUserService) {
        this.boardService = boardService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping(path = "{boardId}")
    public ResponseEntity<Board> getSingleBoard(@PathVariable String boardId) {
        Board board = boardService.getSingleBoard(boardId);
        return ResponseEntity.ok().body(board);
    }

    @PostMapping(path="{boardId}")
    public ResponseEntity<Board> starBoard(@PathVariable String boardId, Principal principal) {
        AppUser appUser = appUserService.getUserByUsername(principal.getName());
        Board board = boardService.starBoard(boardId, appUser);
        return ResponseEntity.ok().body(board);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody NewBoard body, Principal principal) {
        AppUser appUser = appUserService.getUserByUsername(principal.getName());
        Board board = boardService.createBoard(body.boardName, body.background, body.backgroundThumbnail, appUser);
        URI uri = URIFactory.create();
        return ResponseEntity.created(uri).body(board);
    }

    @PutMapping(path = "{boardId}")
    public Board updateBoard(@PathVariable String boardId, @RequestBody Board newBoard) {
        return boardService.updateBoard(boardId, newBoard);
    }

    @DeleteMapping(path = "{boardId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoard(boardId);
    }

    

}
