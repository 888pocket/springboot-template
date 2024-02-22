package com.example.webfullstack.auth.service;

import com.example.webfullstack.auth.domain.dto.request.UserRequest;
import com.example.webfullstack.auth.domain.dto.response.UserResponse;
import com.example.webfullstack.auth.domain.entity.User;
import com.example.webfullstack.auth.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loadUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    public UserResponse readUser(Long userId) {
        User findUser = loadUser(userId);

        return UserResponse.builder()
                .id(findUser.getId())
                .name(findUser.getName())
                .email(findUser.getEmail()).build();
    }

    @Transactional
    public Long createUser(UserRequest request) {
        return userRepository.save(User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()).build()).getId();
    }

    @Transactional
    public void updateUser(Long userId, UserRequest request) {
        User findUser = loadUser(userId);
        findUser.update(request);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User findUser = loadUser(userId);

        userRepository.delete(findUser);
    }
}
