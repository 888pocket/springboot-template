package com.example.webfullstack.auth.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {

    private String name;
    private String email;
    private String password;
}
