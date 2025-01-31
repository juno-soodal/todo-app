package com.example.todoapp.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotEmpty
    private final String toDo;

    @NotEmpty
    private final String password;


    public CreateScheduleRequest(String toDo, String password) {
        this.toDo = toDo;
        this.password = password;
    }
}
