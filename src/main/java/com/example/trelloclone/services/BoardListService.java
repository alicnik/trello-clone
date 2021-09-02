package com.example.trelloclone.services;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardListService {

    private final BoardRepository boardRepository;
    private final BoardListRepository boardListRepository;

    @Autowired
    public BoardListService(BoardRepository boardRepository, BoardListRepository boardListRepository) {
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
    }

    public BoardList getSingleBoardList(Long listId) {
        return boardListRepository.getById(listId);
    }

    public List<BoardList> getListsForSingleBoard(Long boardId) {
        Board board = boardRepository.getById(boardId);
        return boardListRepository.getBoardListByBoard(board);
    }

    public BoardList createBoardList(Long boardId, BoardList newList) {
        Board board = boardRepository.getById(boardId);
        newList.setBoard(board);
        return boardListRepository.save(newList);
    }

    public BoardList updateBoardList(Long listId, BoardList updatedList) throws Exception {
        boolean exists = boardListRepository.existsById(listId);
        if (!exists) {
            throw new Exception("List does not exist");
        }
        return boardListRepository.save(updatedList);
    }
}
