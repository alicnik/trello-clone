package com.example.trelloclone.repositories;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.Board;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/trello_clone_test_db"
})
@TestInstance(PER_CLASS)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void findSingleBoard() {
        AppUser alex = AppUser.builder()
                .username("alex")
                .emailAddress("alex@hotmail.com")
                .password("password")
                .build();
        entityManager.persist(alex);

        Board board = Board.builder()
                .owner(alex)
                .boardName("repository test")
                .build();
        Long createdBoardId = entityManager.persist(board).getId();

        Optional<Board> foundBoard = boardRepository.findById(createdBoardId);
        assertTrue(foundBoard.isPresent());
        assertEquals(foundBoard.get().getId(), createdBoardId);
    }

}