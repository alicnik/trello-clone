package com.example.trelloclone.controllers;

import com.example.trelloclone.helpers.AuthenticationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardControllerTest {

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
    void getSingleCard() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/lists/cards/1")
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void updateCard() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("title", "Updated first title");

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/lists/cards/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(map));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated first title"));
    }

}
