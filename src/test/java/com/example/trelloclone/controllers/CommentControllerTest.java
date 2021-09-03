package com.example.trelloclone.controllers;

import com.example.trelloclone.models.*;
import com.example.trelloclone.repositories.*;
import com.example.trelloclone.services.CommentService;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser alicnik;
    private AppUser chloe;
    private Board board;
    private BoardList boardList;
    private Card card;
    private Comment comment;

    @BeforeAll
    public void setup() {
        alicnik = new AppUser();
        alicnik.setUsername("alicnik");
        alicnik.setEmailAddress("alicnik@hotmail.com");
        alicnik.setPassword(bCryptPasswordEncoder().encode("alicnik"));

//        chloe = new AppUser();
//        chloe.setUsername("chloe");
//        chloe.setEmailAddress("chloe@gmail.com");
//        chloe.setPassword(bCryptPasswordEncoder().encode("chloe"));

        board = new Board();
        board.setBoardName("To-Do List");
        board.setBackground("green");
        board.setOwner(alicnik);

        boardList = new BoardList();
        boardList.setBoard(board);
        boardList.setArchived(false);

        card = new Card();
        card.setAuthor(alicnik);
        card.setBoardList(boardList);
        card.setBoard(board);

        comment = new Comment();
        comment.setAuthor(alicnik);
        comment.setParentCard(card);
        comment.setBody("This is my comment!");

        alicnik = appUserRepository.save(alicnik);
        board = boardRepository.save(board);
        boardList = boardListRepository.save(boardList);
        card = cardRepository.save(card);
        comment = commentRepository.save(comment);
    }

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Test
    public void getSingleComment() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/cards/comments/1");
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("This is my comment!"));
    }


    @Test
    public void createNewComment() throws Exception {

        Comment newComment = new Comment();
        newComment.setParentCard(card);
        newComment.setAuthor(alicnik);
        newComment.setBody("Second comment");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/cards/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newComment));

        this.mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

    }

}
