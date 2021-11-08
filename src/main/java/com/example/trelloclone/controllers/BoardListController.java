package com.example.trelloclone.controllers;

import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.services.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class BoardListController {

    private final BoardListService boardListService;

    @Autowired
    public BoardListController(BoardListService boardListService) {
        this.boardListService = boardListService;
    }

    @GetMapping()
    public List<BoardList> getAllBoardLists() {
        return boardListService.getAllBoardLists();
    }

    @GetMapping(path = "/boards/lists/{listId}")
    public BoardList getSingleBoardList(@PathVariable String listId) {
        return boardListService.getSingleBoardList(listId);
    }

    @GetMapping(path = "/boards/{boardId}/lists")
    public List<BoardList> getListsForSingleBoard(@PathVariable String boardId) {
        return boardListService.getListsForSingleBoard(boardId);
    }

    @PostMapping(path = "/boards/{boardId}/lists")
    public BoardList createList(@PathVariable String boardId, @RequestBody BoardList newList) {
        return boardListService.createBoardList(boardId, newList);
    }

    @PatchMapping(path = "/boards/lists/{listId}")
    public BoardList updateBoardListTitle(
            @PathVariable String listId,
            @RequestBody Map<String, String> body
    ) throws Exception {
        Optional<String> title = Optional.ofNullable(body.get("title"));
        if (title.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return boardListService.updateBoardListTitle(listId, title.get());
    }

    @DeleteMapping(path = "/lists/{listId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBoardList(@PathVariable String listId) {
        boardListService.deleteSingleBoardList(listId);
    }
}

