package com.example.trelloclone.services;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final BoardListRepository boardListRepository;

    @Autowired
    public CardService(CardRepository cardRepository, BoardListRepository boardListRepository) {
        this.cardRepository = cardRepository;
        this.boardListRepository = boardListRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getSingleCard(Long cardId) throws Exception {
        Optional<Card> foundCard = cardRepository.findById(cardId);
        if (foundCard.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        return foundCard.get();
    }

    public Card createNewCard(Long listId, String title, AppUser author) {
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
}
