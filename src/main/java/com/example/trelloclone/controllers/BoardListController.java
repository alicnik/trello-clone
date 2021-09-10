package com.example.trelloclone.controllers;

import com.example.trelloclone.controllers.helpers.BoardListTitleChange;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.services.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return boardListService.getSingleBoardList(Long.valueOf(listId));
    }

    @GetMapping(path = "/boards/{boardId}/lists")
    public List<BoardList> getListsForSingleBoard(@PathVariable String boardId) {
        return boardListService.getListsForSingleBoard(Long.valueOf(boardId));
    }

    @PostMapping(path = "/boards/{boardId}/lists")
    public BoardList createList(@PathVariable String boardId, @RequestBody BoardList newList) {
        return boardListService.createBoardList(Long.valueOf(boardId), newList);
    }

    @PutMapping(path = "/boards/lists/{listId}")
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

