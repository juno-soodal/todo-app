package com.example.todoapp.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    private String toDo;
    @NotEmpty
    private String password;
}
