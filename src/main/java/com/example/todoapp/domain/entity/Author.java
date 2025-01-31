package com.example.todoapp.domain.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Author {

    @Setter
    private Long id;
    private String authorName;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Author(String authorName, String email,String password) {
        this.authorName = authorName;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }
}
