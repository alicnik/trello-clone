package com.example.trelloclone;

import com.example.trelloclone.models.*;
import com.example.trelloclone.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;


@SpringBootApplication
public class TrelloCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloCloneApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(
            AppUserRepository appUserRepository,
            BoardRepository boardRepository,
            BoardListRepository boardListRepository,
            CardRepository cardRepository,
            CommentRepository commentRepository,
            LabelRepository labelRepository
    ) {
        return args -> {

//            AppUser existing = appUserRepository.findByUsername("alicnik");
//
//            if (existing != null) {
//                return;
//            }

            boardRepository.deleteAll();
            boardListRepository.deleteAll();
            cardRepository.deleteAll();
            appUserRepository.deleteAll();

            AppUser alicnik = AppUser.builder()
                    .username("alicnik")
                    .firstName("Alexander")
                    .lastName("Nicholas")
                    .emailAddress("alicnik@hotmail.com")
                    .password(bCryptPasswordEncoder().encode("alicnik"))
                    .build();

            appUserRepository.saveAll(List.of(alicnik));


            Card firstCard = Card.builder()
                    .title("First card")
                    .build();

            Card secondCard = Card.builder()
                    .title("Second card")
                    .build();

            Card thirdCard = Card.builder()
                    .title("Third card")
                    .build();

            Card fourthCard = Card.builder()
                    .title("Fourth card")
                    .build();

            cardRepository.saveAll(List.of(firstCard, secondCard, thirdCard, fourthCard));

            BoardList firstList = BoardList.builder()
                    .title("First List")
                    .cards(List.of(firstCard, secondCard, thirdCard))
                    .build();

            BoardList secondList = BoardList.builder()
                    .title("Second List")
                    .cards(List.of(fourthCard))
                    .build();

            boardListRepository.saveAll(List.of(firstList, secondList));

            Board firstBoard = Board.builder()
                    .boardName("First Board")
                    .background("lightblue")
                    .backgroundThumbnail("lightblue")
                    .lists(List.of(firstList, secondList))
                    .owner(alicnik)
                    .build();

            boardRepository.saveAll(List.of(firstBoard));

            firstList.setBoard(firstBoard);
            secondList.setBoard(firstBoard);

            boardListRepository.saveAll(List.of(firstList, secondList));


            firstCard.setBoard(firstBoard);
            secondCard.setBoard(firstBoard);
            thirdCard.setBoard(firstBoard);
            fourthCard.setBoard(firstBoard);

            firstCard.setBoardList(firstList);
            secondCard.setBoardList(firstList);
            thirdCard.setBoardList(firstList);
            fourthCard.setBoardList(secondList);

            firstCard.setAuthor(alicnik);
            secondCard.setAuthor(alicnik);
            thirdCard.setAuthor(alicnik);
            fourthCard.setAuthor(alicnik);

            cardRepository.saveAll(List.of(firstCard, secondCard, thirdCard, fourthCard));

            firstList.setCards(List.of(firstCard, secondCard, thirdCard));
            secondList.setCards(List.of(fourthCard));
            boardListRepository.saveAll(List.of(firstList, secondList));

//            firstBoard.setCards(List.of(firstCard, secondCard, thirdCard, fourthCard));
//            boardRepository.save(firstBoard);




        };
    }

}
