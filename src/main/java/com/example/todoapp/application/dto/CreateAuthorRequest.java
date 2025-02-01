package com.example.todoapp.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateAuthorRequest {

    @NotEmpty(message = "작서자 명은 필수값입니다.")
    private final String authorName;

    @NotNull(message = "이메일은 필수값입니다.")
    @Email
    private final String email;

    @NotEmpty(message = "패스워드는 필수값입니다.")
    private final String password;

    public CreateAuthorRequest(String authorName, String email, String password) {
        this.authorName = authorName;
        this.email = email;
        this.password = password;
    }
}
