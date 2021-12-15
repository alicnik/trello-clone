package com.example.trelloclone.controllers;

import com.example.trelloclone.controllers.helpers.NewCard;
import com.example.trelloclone.controllers.helpers.URIFactory;
import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.services.AppUserService;
import com.example.trelloclone.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lists")
public class CardController {

    private final CardService cardService;
    private final AppUserService appUserService;

    @Autowired
    public CardController(CardService cardService, AppUserService appUserService) {
        this.cardService = cardService;
        this.appUserService = appUserService;
    }

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping(path = "/cards/{cardId}")
    public Card getSingleCard(@PathVariable String cardId) throws Exception {
        return cardService.getSingleCard(cardId);
    }

    @GetMapping(path = "/{listId}/cards")
    public List<Card> getListCards(@PathVariable String listId) {
        return cardService.getListCards(listId);
    }

    @PostMapping(path = "/{listId}/cards")
    public ResponseEntity<Board> createNewCard(
            Principal principal,
            @PathVariable String listId,
            @RequestBody NewCard newCard
    ) {
        URI uri = URIFactory.create();
        AppUser appUser = appUserService.getUserByUsername(principal.getName());
        Board board = cardService.createNewCard(listId, newCard.title, appUser);
        return ResponseEntity.created(uri).body(board);
    }

    @PatchMapping(path = "/cards/{cardId}")
    public Card updateCard(@PathVariable String cardId, @RequestBody Map<String, Object> body) {
        return cardService.updateCard(cardId, body);
    }
}

