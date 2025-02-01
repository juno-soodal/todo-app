package com.example.todoapp.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    private String toDo;
    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    private String password;
}
