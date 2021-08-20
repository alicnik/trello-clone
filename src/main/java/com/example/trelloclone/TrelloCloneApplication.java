package com.example.trelloclone;

import com.example.trelloclone.models.Board;
import com.example.trelloclone.models.User;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class TrelloCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrelloCloneApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			BoardRepository boardRepository
	) {
		return args -> {
			User alicnik = User.builder()
					.username("alicnik")
					.emailAddress("alicnik@hotmail.com")
					.password("alicnik")
					.build();

			User chloe = User.builder()
					.username("chloebuilds")
					.emailAddress("chloe@gmail.com")
					.password("chloe")
					.build();

			Board toDoList = Board.builder()
					.name("To-Do List")
					.background("green")
					.owner(alicnik)
					.build();

			userRepository.saveAll(List.of(alicnik, chloe));
			boardRepository.save(toDoList);
		};
	}

}
