package com.example.trelloclone.controllers;

import com.example.trelloclone.models.AppUser;
import com.example.trelloclone.models.BoardList;
import com.example.trelloclone.models.Card;
import com.example.trelloclone.models.Comment;
import com.example.trelloclone.repositories.AppUserRepository;
import com.example.trelloclone.repositories.BoardListRepository;
import com.example.trelloclone.repositories.CardRepository;
import com.example.trelloclone.repositories.CommentRepository;
import com.example.trelloclone.services.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentControllerTest {

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
    private CardRepository cardRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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

//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/api/v1/cards/comments")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString());
//
//        this.mockMvc.perform(requestBuilder)
//                .andExpect(status().isCreated());

    }

}
