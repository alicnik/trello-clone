package com.example.trelloclone.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AuthenticationHelper {

    public static String getToken(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
        AtomicReference<String> token = new AtomicReference<>();
        Map<String, String> body = new HashMap<>();
        body.put("username", "alicnik");
        body.put("password", "alicnik");
        String content = objectMapper.writeValueAsString(body);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request).andDo(mvcResult -> {
            String responseBody = mvcResult.getResponse().getContentAsString();
            Map<String, String> bodyObject = objectMapper.readValue(responseBody, Map.class);
            token.set(bodyObject.get("access_token"));
        });
        return token.get();
    }
}
