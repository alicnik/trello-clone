package com.example.trelloclone.services;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, CardRepository cardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getSingleBoard(String boardId) {
        return boardRepository.findById(boardId);
    }

    public Board createBoard(String boardName, AppUser appUser) {
        Board board = Board.builder()
                .boardName(boardName)
                .owner(appUser)
                .build();
        return boardRepository.save(board);
    }

    public void deleteBoard(String boardId) {
        boardRepository.deleteById(boardId);
    }

    public Board updateBoard(String boardId, Board newBoard) {
        Board board = boardRepository.getById(boardId);
        board.setLists(newBoard.getLists());
        return boardRepository.save(board);
    }
}
