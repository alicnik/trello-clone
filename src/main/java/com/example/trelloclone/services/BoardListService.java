package com.example.trelloclone.services;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.BoardRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class BoardListService {

    private final BoardRepository boardRepository;
    private final BoardListRepository boardListRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public BoardListService(
            BoardRepository boardRepository,
            BoardListRepository boardListRepository,
            ObjectMapper objectMapper
    ) {
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
        this.objectMapper = objectMapper;
    }

    public BoardList getSingleBoardList(Long listId) {
        System.out.println("IN THE SERVICE GETTING A SINGLE LIST");
        return boardListRepository.getById(listId);
    }

    public List<BoardList> getListsForSingleBoard(Long boardId) {
        Board board = boardRepository.getById(boardId);
        log.info(board.toString());
        return boardListRepository.getBoardListByBoard(board);
    }

    public BoardList createBoardList(Long boardId, BoardList newList) {
        Board board = boardRepository.getById(boardId);
        int listPosition = board.getLists().size();
        newList.setBoard(board);
        newList.setPosition(listPosition);
        return boardListRepository.save(newList);
    }

    public BoardList updateBoardListTitle(Long listId, String newTitle) throws Exception {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isEmpty()) {
            throw new Exception("List does not exist");
        }
        boardList.ifPresent(boardList1 -> boardList1.setTitle(newTitle));
        BoardList updatedList = boardList.get();
        return boardListRepository.save(updatedList);
    }

    public void deleteSingleBoardList(Long listId) {
        boardListRepository.deleteById(listId);
    }
}
