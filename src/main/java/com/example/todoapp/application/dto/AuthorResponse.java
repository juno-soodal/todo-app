package com.example.todoapp.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthorResponse {

    private final Long id;
    private final String authorName;
    private final String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    public AuthorResponse(Long id, String authorName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.authorName = authorName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
