package com.example.webfullstack.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.webfullstack.auth.domain.dto.request.UserRequest;
import com.example.webfullstack.auth.domain.dto.response.UserResponse;
import com.example.webfullstack.auth.domain.entity.User;
import com.example.webfullstack.auth.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void createUser_IntegrationTest() {
        // Given
        UserRequest request = UserRequest.builder()
                .name("John")
                .email("john@example.com")
                .password("password").build();

        // When
        Long userId = userService.createUser(request);

        // Then
        assertNotNull(userId);
    }

    @Test
    public void readUser_IntegrationTest() {
        // Given
        UserRequest request = UserRequest.builder()
                .name("John")
                .email("john@example.com")
                .password("password").build();
        Long userId = userService.createUser(request);

        // When
        UserResponse userResponse = userService.readUser(userId);

        // Then
        assertNotNull(userResponse);
        assertEquals("John", userResponse.getName());
        assertEquals("john@example.com", userResponse.getEmail());
    }

    @Test
    public void readUser_NotFound_IntegrationTest() {
        // Given
        Long nonExistingUserId = 999L; // 존재하지 않는 사용자 ID

        // When, Then
        assertThrows(NoSuchElementException.class, () -> userService.readUser(nonExistingUserId));
    }

    @Test
    public void updateUser_IntegrationTest() {
        // Given
        UserRequest request = UserRequest.builder()
                .name("John")
                .email("john@example.com")
                .password("password").build();
        Long userId = userService.createUser(request);

        // When
        UserRequest updateRequest = UserRequest.builder()
                .name("updateUser")
                .email("update@example.com")
                .password("updatePassword").build();
        userService.updateUser(userId, updateRequest);

        // Then
        Optional<User> foundUser = userRepository.findById(userId);
        assertTrue(foundUser.isPresent());
        assertEquals(updateRequest.getName(), foundUser.get().getName());
        assertEquals(updateRequest.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void deleteUser_IntegrationTest() {
        // Given
        UserRequest request = UserRequest.builder()
                .name("John")
                .email("john@example.com")
                .password("password").build();
        Long userId = userService.createUser(request);

        // When
        userService.deleteUser(userId);

        // Then
        Optional<User> foundUser = userRepository.findById(userId);
        assertTrue(foundUser.isEmpty());
    }
}