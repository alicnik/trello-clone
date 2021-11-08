package com.example.trelloclone.services;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final BoardListRepository boardListRepository;

    @Autowired
    public CardService(
            CardRepository cardRepository,
            BoardListRepository boardListRepository
    ) {
        this.cardRepository = cardRepository;
        this.boardListRepository = boardListRepository;
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

    public Card createNewCard(String listId, String title, AppUser author) {
        Optional<BoardList> boardList = boardListRepository.findById(listId);
        if (boardList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found");
        }
        BoardList foundBoardList = boardList.get();
        Card newCard = Card.builder()
                .title(title)
                .author(author)
                .boardList(foundBoardList)
                .board(foundBoardList.getBoard())
                .build();

        return cardRepository.save(newCard);

    }

    public Card updateCard(String cardId, Map<String, Object> patchUpdate) {
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

        return cardRepository.save(cardToUpdate);
    }

    public List<Card> getListCards(String listId) {
        return cardRepository.findCardsByBoardListId(listId);
    }
}
