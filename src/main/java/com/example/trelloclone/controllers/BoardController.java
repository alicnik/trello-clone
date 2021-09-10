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
@RequestMapping("api/v1/boards")
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
    public Optional<Board> getSingleBoard(@PathVariable String boardId) {
        return boardService.getSingleBoard(Long.valueOf(boardId));
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody NewBoard body, Principal principal) {
        log.info(body.toString());
        AppUser appUser = appUserService.getUserByUsername(principal.getName());
        Board board = boardService.createBoard(body.boardName, appUser);
        URI uri = URIFactory.create();
        return ResponseEntity.created(uri).body(board);
    }

    @DeleteMapping(path = "{boardId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable String boardId) {
        boardService.deleteBoard(Long.valueOf(boardId));
    }

}
