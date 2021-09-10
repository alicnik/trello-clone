package com.example.trelloclone.controllers;

import com.example.trelloclone.TestConfiguration;
import com.example.trelloclone.repositories.CommentRepository;
import com.example.trelloclone.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(TestConfiguration.class)
@WebMvcTest(CommentController.class)
@TestInstance(PER_CLASS)
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

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


//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/api/v1/cards/comments")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString("hello"));
//
//        this.mockMvc.perform(requestBuilder)
//                .andExpect(status().isCreated());

    }

}
