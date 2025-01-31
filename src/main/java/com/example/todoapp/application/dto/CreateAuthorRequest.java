package com.example.todoapp.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateAuthorRequest {

    @NotEmpty
    private final String authorName;

    @NotNull
    @Email
    private final String email;

    @NotEmpty
    private final String password;

    public CreateAuthorRequest(String authorName, String email, String password) {
        this.authorName = authorName;
        this.email = email;
        this.password = password;
    }
}
