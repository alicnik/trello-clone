package com.example.trelloclone.services;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardListService {

    private final BoardRepository boardRepository;
    private final BoardListRepository boardListRepository;

    @Autowired
    public BoardListService(
            BoardRepository boardRepository,
            BoardListRepository boardListRepository
    ) {
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
    }

    public BoardList getSingleBoardList(String listId) {
        System.out.println("IN THE SERVICE GETTING A SINGLE LIST");
        return boardListRepository.getById(listId);
    }

    public List<BoardList> getListsForSingleBoard(String boardId) {
        Board board = boardRepository.getById(boardId);
        log.info(board.toString());
        return boardListRepository.getBoardListByBoard(board);
    }

    public BoardList createBoardList(String boardId, BoardList newList) {
        Board board = boardRepository.getById(boardId);
        int listPosition = board.getLists().size();
        newList.setBoard(board);
        newList.setPosition(listPosition);
        return boardListRepository.save(newList);
    }

    public BoardList updateBoardListTitle(String listId, String newTitle) throws Exception {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isEmpty()) {
            throw new Exception("List does not exist");
        }
        boardList.ifPresent(boardList1 -> boardList1.setTitle(newTitle));
        BoardList updatedList = boardList.get();
        return boardListRepository.save(updatedList);
    }

    public void deleteSingleBoardList(String listId) {
        boardListRepository.deleteById(listId);
    }

    public List<BoardList> getAllBoardLists() {
        return boardListRepository.findAll();
    }
}
