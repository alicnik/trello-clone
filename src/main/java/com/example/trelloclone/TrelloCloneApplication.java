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

            AppUser chloe = AppUser.builder()
                    .username("chloe")
                    .emailAddress("chloe@gmail.com")
                    .password(bCryptPasswordEncoder().encode("chloe"))
                    .build();

            appUserRepository.saveAll(List.of(alicnik, chloe));

            Card firstCard = Card.builder()
                    .title("Take out rubbish")
                    .build();

            cardRepository.saveAll(List.of(firstCard));

            BoardList firstList = BoardList.builder()
                    .title("First List")
                    .cards(List.of(firstCard))
                    .build();

            boardListRepository.saveAll(List.of(firstList));

            Board firstBoard = Board.builder()
                    .boardName("First Board")
                    .owner(alicnik)
                    .lists(List.of(firstList))
                    .cards(List.of(firstCard))
                    .build();

            boardRepository.saveAll(List.of(firstBoard));

        };
    }

}
