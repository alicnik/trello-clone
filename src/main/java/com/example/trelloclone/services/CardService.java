package com.example.trelloclone.services;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CardService {

    private final CardRepository cardRepository;
    private final BoardListRepository boardListRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CardService(
            CardRepository cardRepository,
            BoardListRepository boardListRepository,
            BoardRepository boardRepository
    ) {
        this.cardRepository = cardRepository;
        this.boardListRepository = boardListRepository;
        this.boardRepository = boardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getSingleCard(String cardId) throws Exception {
        Optional<Card> foundCard = cardRepository.findById(cardId);
        if (foundCard.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        return foundCard.get();
    }

    public Board createNewCard(String listId, String title, AppUser author) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found");
        }
        BoardList foundBoardList = boardList.get();
        Card newCard = Card.builder()
                .title(title)
                .author(author)
//                .boardList(foundBoardList)
//                .board(foundBoardList.getBoard())
                .build();
        log.info("saving card for the first time");
        Card savedCard = cardRepository.save(newCard);

        foundBoardList.getCards().add(newCard);
        log.info("saving list");
        boardListRepository.save(foundBoardList);

        savedCard.setBoardList(foundBoardList);
        savedCard.setBoard(foundBoardList.getBoard());
        log.info("saving card for the second time");
        Card updatedCard = cardRepository.save(savedCard);
        log.info("card board is {}", updatedCard.getBoard());

        Board boardToUpdate = foundBoardList.getBoard();
        return boardRepository.save(boardToUpdate);

    }

    public Board updateCard(String cardId, Map<String, Object> patchUpdate) {
        Optional<Card> cardToFind = cardRepository.findById(cardId);
        if (cardToFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card does not exist");
        }

        Card cardToUpdate = cardToFind.get();
        patchUpdate.remove("id");

        patchUpdate.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Card.class, key);
            if (field == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property " + key + " does not exist");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, cardToUpdate, value);
        });

        Card savedCard = cardRepository.save(cardToUpdate);
        Board board = boardRepository.getById(savedCard.getBoard().getId());
        return board;
    }

    public List<Card> getListCards(String listId) {
        return cardRepository.findCardsByBoardListId(listId);
    }
}
