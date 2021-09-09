package com.example.trelloclone.controllers;

import com.example.trelloclone.helpers.BoardListTitleChange;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.services.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardListController {

    private final BoardListService boardListService;

    @Autowired
    public BoardListController(BoardListService boardListService) {
        this.boardListService = boardListService;
    }

    @GetMapping(path = "/lists/{listId}")
    public BoardList getSingleBoardList(@PathVariable String listId) {
        System.out.println("GETTING A SINGLE LIST");
        return boardListService.getSingleBoardList(Long.valueOf(listId));
    }

    @GetMapping(path = "/{boardId}/lists")
    public List<BoardList> getListsForSingleBoard(@PathVariable String boardId) {
        return boardListService.getListsForSingleBoard(Long.valueOf(boardId));
    }

    @PostMapping(path = "/{boardId}/lists")
    public BoardList createList(@PathVariable String boardId, @RequestBody BoardList newList) {
        return boardListService.createBoardList(Long.valueOf(boardId), newList);
    }

    @PutMapping(path = "/lists/{listId}")
    public BoardList updateBoardListTitle(
            @PathVariable String listId,
            @RequestBody BoardListTitleChange boardListTitleChange
    ) throws Exception {
        return boardListService.updateBoardListTitle(Long.valueOf(listId), boardListTitleChange.title);
    }

    @DeleteMapping(path = "/lists/{listId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBoardList(@PathVariable String listId) {
        boardListService.deleteSingleBoardList(Long.valueOf(listId));
    }
}

