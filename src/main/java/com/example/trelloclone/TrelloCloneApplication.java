package com.example.trelloclone;

import com.example.trelloclone.models.*;
import com.example.trelloclone.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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

			Board firstBoard = Board.builder()
					.boardName("To-Do List")
					.background("green")
					.owner(alicnik)
					.build();

			Board secondBoard = Board.builder()
					.boardName("Second Board")
					.background("blue")
					.owner(alicnik)
					.build();

			BoardList firstList = BoardList.builder()
					.board(firstBoard)
					.title("First List")
					.archived(false)
					.build();

			BoardList secondList = BoardList.builder()
					.board(firstBoard)
					.title("Second List")
					.position(1)
					.archived(false)
					.build();

			BoardList thirdList = BoardList.builder()
					.board(secondBoard)
					.title("Third List")
					.archived(false)
					.build();

			Card firstCard = Card.builder()
					.title("First card")
					.author(alicnik)
					.boardList(firstList)
					.board(firstBoard)
					.build();

			Card secondCard = Card.builder()
					.title("Second card")
					.author(alicnik)
					.boardList(firstList)
					.board(firstBoard)
					.build();

			Card thirdCard = Card.builder()
					.title("Third card")
					.author(alicnik)
					.boardList(firstList)
					.board(firstBoard)
					.build();

			Card fourthCard = Card.builder()
					.title("Fourth card")
					.author(alicnik)
					.boardList(firstList)
					.board(firstBoard)
					.build();

			Card fifthCard = Card.builder()
					.title("Fifth card")
					.author(alicnik)
					.boardList(firstList)
					.board(firstBoard)
					.build();

			Comment comment = Comment.builder()
					.author(chloe)
					.parentCard(firstCard)
					.body("This is my comment!")
					.build();

			appUserRepository.saveAll(List.of(alicnik, chloe));
			boardRepository.saveAll(List.of(firstBoard, secondBoard));
			boardListRepository.saveAll(List.of(firstList, secondList, thirdList));
			cardRepository.saveAll(List.of(firstCard, secondCard, thirdCard, fourthCard, fifthCard));
			commentRepository.save(comment);
		};
	}

}
