package com.example.trelloclone.controllers;

import com.example.trelloclone.services.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards/lists")
public class BoardListController {

    private BoardListService boardListService;

    @Autowired
    public BoardListController(BoardListService boardListService) {
        this.boardListService = boardListService;
    }
}
