package com.example.trelloclone.controllers;

import com.example.trelloclone.helpers.AuthenticationHelper;
import com.example.trelloclone.repositories.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String token;

    @BeforeAll
    void getToken() throws Exception {
        this.token = AuthenticationHelper.getToken(mockMvc, objectMapper);
    }

    @Test
    void getAllBoardLists() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/lists")
                .header("Authorization", "Bearer " + token);
        mockMvc.perform(request).andExpect(status().isOk());
    }
}
