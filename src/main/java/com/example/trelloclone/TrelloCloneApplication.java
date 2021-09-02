package com.example.trelloclone;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import com.example.trelloclone.repositories.BoardRepository;
import com.example.trelloclone.repositories.AppUserRepository;
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
			BoardRepository boardRepository
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

			appUserRepository.saveAll(List.of(alicnik, chloe));
			boardRepository.save(toDoList);
		};
	}

}
