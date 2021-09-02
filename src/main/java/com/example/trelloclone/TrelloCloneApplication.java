package com.example.trelloclone;

import com.example.trelloclone.models.*;
import com.example.trelloclone.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
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
			CommentRepository commentRepository
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

			Board toDoList = Board.builder()
					.boardName("To-Do List")
					.background("green")
					.owner(alicnik)
					.build();

			BoardList boardList = BoardList.builder()
					.board(toDoList)
					.archived(false)
					.build();

			Card card = Card.builder()
					.author(alicnik)
					.boardList(boardList)
					.board(toDoList)
					.build();

			Comment comment = Comment.builder()
					.author(chloe)
					.parentCard(card)
					.body("This is my comment!")
					.build();

			appUserRepository.saveAll(List.of(alicnik, chloe));
			boardRepository.save(toDoList);
			boardListRepository.save(boardList);
			cardRepository.save(card);
			commentRepository.save(comment);
		};
	}

}
