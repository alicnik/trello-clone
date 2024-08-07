package com.example.trelloclone.controllers;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.services.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/lists")
    public List<BoardList> getAllBoardLists() {
        return boardListService.getAllBoardLists();
    }

    @GetMapping(path = "/boards/lists/{listId}")
    public BoardList getSingleBoardList(@PathVariable String listId) {
        BoardList list= boardListService.getSingleBoardList(listId);
        System.out.println(list.getBoard());
        return list;
    }

    @GetMapping(path = "/boards/{boardId}/lists")
    public List<BoardList> getListsForSingleBoard(@PathVariable String boardId) {
        return boardListService.getListsForSingleBoard(boardId);
    }

    @PostMapping(path = "/boards/{boardId}/lists")
    public Board createList(@PathVariable String boardId, @RequestBody BoardList newList) {
        return boardListService.createBoardList(boardId, newList);
    }

    @PatchMapping(path = "/boards/lists/{listId}")
    public Board updateBoardListTitle(
            @PathVariable String listId,
            @RequestBody Map<String, String> body
    ) throws Exception {
        Optional<String> title = Optional.ofNullable(body.get("title"));
        if (title.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return boardListService.updateBoardListTitle(listId, title.get());

    }
    @PutMapping(path = "/boards/{boardId}/lists/{listId}/cards")
    public ResponseEntity<Board> updateBoardListCards(
            @PathVariable String boardId,
            @PathVariable String listId,
            @RequestBody List<Card> newCards
    ) throws Exception {
        System.out.println("boardId");
        System.out.println(boardId);
        System.out.println("listId");
        System.out.println(listId);
        System.out.println("newCards");
        System.out.println(newCards);
        Board updatedBoard = boardListService.updateBoardListCards(boardId, listId, newCards);
        System.out.println("IN PUT MAPPING - UPDATE SUCCESSFUL");
        return ResponseEntity.ok().body(updatedBoard);
    }

    @DeleteMapping(path = "/lists/{listId}")
    public Board deleteBoardList(@PathVariable String listId) {
        return boardListService.deleteSingleBoardList(listId);
    }
}

