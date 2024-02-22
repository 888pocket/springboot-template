package com.example.webfullstack.auth.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUser_IntegrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"name\": \"testUser\","
                                        + "\"email\": \"test@mail.com\","
                                        + "\"password\": \"testPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void readUser_IntegrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void readUser_NotFound_IntegrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateUser_IntegrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/auth/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"name\": \"modifyUser\","
                                        + "\"email\": \"modify@mail.com\","
                                        + "\"password\": \"modifyPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteUser_IntegrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}