package com.example.webfullstack.auth.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {

    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
