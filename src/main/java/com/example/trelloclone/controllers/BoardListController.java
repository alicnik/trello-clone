package com.example.trelloclone.controllers;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.services.BoardListService;
import com.example.trelloclone.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardListController {

    private BoardListService boardListService;

    @Autowired
    public BoardListController(BoardService boardService, BoardListService boardListService) {
        this.boardListService = boardListService;
    }

    @GetMapping(path = "/lists/{listId}")
    public BoardList getSingleBoardList(@PathVariable String listId) {
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
    public BoardList updateBoardList(
            @PathVariable String listId,
            @RequestBody BoardList updatedList
    ) throws Exception {
        return boardListService.updateBoardList(Long.valueOf(listId), updatedList);
    }
}
