package com.example.trelloclone.controllers;

import com.example.trelloclone.controllers.helpers.NewBoard;
import com.example.trelloclone.repositories.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    void getAllBoards() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/boards/");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void getSingleBoard() throws  Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/boards/1");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void createNewBoard() throws Exception {
        NewBoard newBoard = new NewBoard( "integration-test");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBoard));

        mockMvc.perform(request).andExpect(status().isCreated());
    }

}