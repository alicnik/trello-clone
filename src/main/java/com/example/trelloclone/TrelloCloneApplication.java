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
            AppUser alicnik = AppUser.builder()
                    .username("alicnik")
                    .emailAddress("alicnik@hotmail.com")
                    .password(bCryptPasswordEncoder().encode("alicnik"))
                    .build();

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

            BoardList firstList = BoardList.builder()
                    .title("First List")
                    .cards(List.of(firstCard, secondCard, thirdCard))
                    .build();

            BoardList secondList = BoardList.builder()
                    .title("Second List")
                    .cards(List.of(fourthCard))
                    .build();

            Board firstBoard = Board.builder()
                    .boardName("First Board")
                    .background("lightblue")
                    .backgroundThumbnail("lightblue")
                    .lists(List.of(firstList, secondList))
                    .cards(List.of(firstCard, secondCard, thirdCard, fourthCard))
                    .owner(alicnik)
                    .build();

            firstList.setBoard(firstBoard);
            secondList.setBoard(firstBoard);
            firstCard.setBoard(firstBoard);
            secondCard.setBoard(firstBoard);
            thirdCard.setBoard(firstBoard);
            fourthCard.setBoard(firstBoard);

            appUserRepository.saveAll(List.of(alicnik));
            boardRepository.saveAll(List.of(firstBoard));



        };
    }

}
