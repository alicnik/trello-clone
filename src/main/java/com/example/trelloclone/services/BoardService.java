package com.example.trelloclone.services;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.repositories.AppUserRepository;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, AppUserRepository appUserRepository) {
        this.boardRepository = boardRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getSingleBoard(String boardId) {
        Optional<Board> board= boardRepository.findById(boardId);
        if (board.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board does not exist");
        }
        return board.get();
    }

    public Board createBoard(String boardName, String background, String backgroundThumbnail, AppUser appUser) {
        Board board = Board.builder()
                .boardName(boardName)
                .background(background)
                .backgroundThumbnail(backgroundThumbnail)
                .owner(appUser)
                .build();
        return boardRepository.save(board);
    }

    public void deleteBoard(String boardId) {
        Optional<Board> optional = boardRepository.findById(boardId);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }

//        Board board = optional.get();
//
//        for (AppUser user : board.getRecentlyViewedBy()) {
//            user.getRecentBoards().remove(board);
//            appUserRepository.save(user);
//        }
//
//        for (AppUser user : board.getStarredBy()) {
//            user.getStarredBoards().remove(board);
//            appUserRepository.save(user);
//        }
//
//        board.getRecentlyViewedBy().clear();
//        board.getStarredBy().clear();
//
//        log.info("Saving");
//        boardRepository.save(board);
//
//
//        log.info("Deleting");
        boardRepository.deleteById(boardId);
    }

    public Board updateBoard(String boardId, Board newBoard) {
        Board board = boardRepository.getById(boardId);
        List<BoardList> newLists = newBoard.getLists();
        newLists.forEach(l -> l.setBoard(board));
        board.setLists(newLists);
        board.setBoardName(newBoard.getBoardName());
        return boardRepository.save(board);
    }

    public Board starBoard(String boardId, AppUser appUser) {
        Board board = boardRepository.getById(boardId);
        List<AppUser> currentStarredBy = board.getStarredBy();
        if (currentStarredBy.contains(appUser)) {
            board.getStarredBy().remove(appUser);
        } else {
            board.getStarredBy().add(appUser);
        }
        return boardRepository.save(board);
    }
}
