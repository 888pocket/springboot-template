package com.example.webfullstack.auth.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.webfullstack.auth.domain.dto.request.UserRequest;
import org.junit.jupiter.api.Test;

class UserUnitTest {

    @Test
    public void testConstructor() {
        // Given
        String name = "John";
        String email = "john@example.com";
        String password = "password";

        // When
        User user = new User(name, email, password);

        // Then
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testBuilder() {
        // Given
        String name = "Jane";
        String email = "jane@example.com";
        String password = "password";

        // When
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        // Then
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testUpdate() {
        // Given
        String name = "Jane";
        String email = "jane@example.com";
        String password = "password";

        User user = new User("Old Name", "old@example.com", "oldpassword");
        UserRequest request = UserRequest.builder()
                .name(name)
                .email(email)
                .password(password).build();

        // When
        User updatedUser = user.update(request);

        // Then
        assertEquals(name, updatedUser.getName());
        assertEquals(email, updatedUser.getEmail());
        assertEquals(password, updatedUser.getPassword());
    }
}