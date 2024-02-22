package com.example.webfullstack.auth.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.webfullstack.auth.domain.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser_UnitTest() {
        // Given
        User user = User.builder()
                .name("John")
                .email("john@example.com")
                .password("password")
                .build();

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertTrue(savedUser.getId() != null);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void findById_UnitTest() {
        // Given
        User user = User.builder()
                .name("John")
                .email("john@example.com")
                .password("password")
                .build();
        Long id = userRepository.save(user).getId();

        // When
        User foundUser = userRepository.findById(id).orElse(null);

        // Then
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }

    @Test
    public void findByEmail_UnitTest() {
        // Given
        String email = "john@example.com";
        User user = User.builder()
                .name("John")
                .email(email)
                .password("password")
                .build();
        userRepository.save(user);

        // When
        User foundUser = userRepository.findByEmail(email).orElse(null);

        // Then
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }

    @Test
    public void saveAndDeleteUser_UnitTest() {
        // Given
        User user = User.builder()
                .name("John")
                .email("john@example.com")
                .password("password")
                .build();

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());

        // When
        userRepository.delete(savedUser);

        // Then
        Optional<User> deletedUserOptional = userRepository.findById(savedUser.getId());
        assertTrue(deletedUserOptional.isEmpty());
    }
}