package com.example.todoapp.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Author {

    private Long id;
    private final String authorName;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public Author(String authorName, String email,String password) {
        this.authorName = authorName;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }



    public Author(Long id, String authorName, String email,String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.authorName = authorName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Author withId(Long id, Author author) {
        return new Author(id, author.getAuthorName(), author.getEmail(),author.getPassword(), author.getCreatedAt(), author.getModifiedAt());
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }
}
