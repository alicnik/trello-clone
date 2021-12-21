package com.example.trelloclone.services;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.CardRepository;
import com.example.trelloclone.repositories.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardListService {

    private final BoardRepository boardRepository;
    private final BoardListRepository boardListRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BoardListService(
            BoardRepository boardRepository,
            BoardListRepository boardListRepository,
            CardRepository cardRepository,
            CommentRepository commentRepository
    ) {
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
        this.cardRepository = cardRepository;
        this.commentRepository = commentRepository;
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

    public Board createBoardList(String boardId, BoardList newList) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (optionalBoard.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
        Board board = optionalBoard.get();
        newList.setBoard(board);
        newList.setCards(List.of());
        BoardList savedList = boardListRepository.save(newList);
        board.getLists().add(savedList);
        return boardRepository.save(board);
    }

    public Board updateBoardListTitle(String listId, String newTitle) throws Exception {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isEmpty()) {
            throw new Exception("List does not exist");
        }
        boardList.ifPresent(list -> list.setTitle(newTitle));
        BoardList updatedList = boardListRepository.save(boardList.get());
        return boardRepository.getById(updatedList.getBoard().getId());
    }

    public Board deleteSingleBoardList(String listId) {
        Optional<BoardList> optional = boardListRepository.findById(listId);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found");
        }
        String boardId = optional.get().getBoard().getId();
        optional.get().getCards().forEach(card -> {
            card.getComments().forEach(comment -> {
                commentRepository.deleteById(comment.getId());
            });
        });
        boardListRepository.deleteById(listId);
        Board updatedBoard = boardRepository.getById(boardId);
        List<BoardList> newLists = updatedBoard.getLists().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        updatedBoard.setLists(newLists);
        return boardRepository.save(updatedBoard);
    }

    public List<BoardList> getAllBoardLists() {
        return boardListRepository.findAll();
    }

    public Board updateBoardListCards(String boardId, String listId, List<Card> newCards) throws Exception {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isEmpty()) {
            throw new Exception("List does not exist");
        }
        BoardList listToUpdate = boardList.get();
        newCards.forEach(c -> c.setBoardList(listToUpdate));
        listToUpdate.setCards(newCards);
        boardListRepository.save(listToUpdate);
        return boardRepository.getById(boardId);
    }
}
