package com.example.todoapp.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    @Size(max = 200, message = "할일은 최대 200자 이내로 작성해주세요")
    private final String toDo;
    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    private final String password;

    public UpdateScheduleRequest(String toDo, String password) {
        this.toDo = toDo;
        this.password = password;
    }
}
