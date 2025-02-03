package com.example.todoapp.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotEmpty(message = "할일은 필수값입니다.")
    @Size(max = 200,message = "할일은 최대 200자 이내로 작성해주세요")
    private final String toDo;

    @NotEmpty(message = "패스워드는 필수값입니다.")
    private final String password;


    public CreateScheduleRequest(String toDo, String password) {
        this.toDo = toDo;
        this.password = password;
    }
}
