package com.example.trelloclone.services;

import com.example.trelloclone.repositories.BoardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardListService {

    private BoardListRepository boardListRepository;

    @Autowired
    public BoardListService(BoardListRepository boardListRepository) {
        this.boardListRepository = boardListRepository;
    }
}
