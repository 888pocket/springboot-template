package com.example.webfullstack.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.webfullstack.auth.domain.dto.request.UserRequest;
import com.example.webfullstack.auth.domain.dto.response.UserResponse;
import com.example.webfullstack.auth.service.UserService;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createUser_UnitTest() throws Exception {
        when(userService.createUser(any(UserRequest.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\","
                                + "\"email\": \"test@example.com\","
                                + "\"password\": \"testPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void readUser_UnitTest() throws Exception {
        UserResponse response = UserResponse.builder()
                .id(1L)
                .name("testUser")
                .email("test@example.com").build();
        given(userService.readUser(1L)).willReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    public void readUser_NotFound_UnitTest() throws Exception {
        given(userService.readUser(anyLong())).willThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateUser_UnitTest() throws Exception {
        doNothing().when(userService).updateUser(anyLong(), any(UserRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/auth/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"updateUser\","
                                + "\"email\": \"update@example.com\","
                                + "\"password\": \"updatePassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteUser_UnitTest() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}