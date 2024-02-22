package com.example.webfullstack.auth.domain.entity;

import com.example.webfullstack.auth.domain.dto.request.UserRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
//@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")  // soft delete
//@SQLRestriction("deleted = false")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
//    private boolean deleted = false;

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User update(UserRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = request.getPassword();

        return this;
    }
}
