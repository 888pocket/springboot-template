package com.example.webfullstack.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.webfullstack.auth.domain.dto.request.UserRequest;
import com.example.webfullstack.auth.domain.dto.response.UserResponse;
import com.example.webfullstack.auth.domain.entity.User;
import com.example.webfullstack.auth.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceUnitTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void createUser_UnitTest() {
        // Given
        UserRequest request = UserRequest.builder()
                .name("John")
                .email("john@example.com")
                .password("password").build();
        User user = new User("John", "john@example.com", "password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        Long userId = userService.createUser(request);

        // Then
        assertEquals(user.getId(), userId);
    }

    @Test
    public void readUser_UnitTest() {
        // Given
        User user = new User("John", "john@example.com", "password");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When
        UserResponse userResponse = userService.readUser(1L);

        // Then
        assertEquals(user.getName(), userResponse.getName());
        assertEquals(user.getEmail(), userResponse.getEmail());
    }

    @Test
    public void updateUser_UnitTest() {
        // Given
        Long userId = 1L;
        UserRequest request = UserRequest.builder()
                .name("Jane")
                .email("jane@example.com")
                .password("newPassword").build();
        User user = new User("John", "john@example.com", "password");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When
        userService.updateUser(userId, request);

        // Then
        assertEquals(request.getName(), user.getName());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
    }
}