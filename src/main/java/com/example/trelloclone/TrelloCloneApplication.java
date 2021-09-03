package com.example.trelloclone;

import com.example.trelloclone.models.*;
import com.example.trelloclone.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.ValidationEventLocator;
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
//			AppUser alicnik = new AppUser();
//			alicnik.setUsername("alicnik");
//			alicnik.setEmailAddress("alicnik@hotmail.com");
//			alicnik.setPassword(bCryptPasswordEncoder().encode("alicnik"));
//
//			AppUser chloe = new AppUser();
//			chloe.setUsername("chloe");
//			chloe.setEmailAddress("chloe@gmail.com");
//			chloe.setPassword(bCryptPasswordEncoder().encode("chloe"));
//
//			Board board = new Board();
//			board.setBoardName("To-Do List");
//			board.setBackground("green");
//			board.setOwner(alicnik);
//
//			BoardList boardList = new BoardList();
//			boardList.setBoard(board);
//			boardList.setArchived(false);
//
//			Card card = new Card();
//			card.setAuthor(alicnik);
//			card.setBoardList(boardList);
//			card.setBoard(board);
//
//			Comment comment = new Comment();
//			comment.setAuthor(chloe);
//			comment.setParentCard(card);
//			comment.setBody("This is my comment!");
//
//			appUserRepository.saveAll(List.of(alicnik, chloe));
//			boardRepository.save(board);
//			boardListRepository.save(boardList);
//			cardRepository.save(card);
//			commentRepository.save(comment);
		};
	}

}
