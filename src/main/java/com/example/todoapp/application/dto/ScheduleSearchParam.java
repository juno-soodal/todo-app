package com.example.todoapp.application.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleSearchParam {
    private final LocalDate modifiedAt;
    @Min(value = 1, message = "페이지 번호는 0보다 작을 수 없습니다.")
    private final int page;
    @Min(value = 1, message = "사이즈는 0보다 작을 수 없습니다.")
    private final int size;

    public ScheduleSearchParam(LocalDate modifiedAt, int page, int size) {
        this.modifiedAt = modifiedAt;
        this.page = page;
        this.size = size;
    }
}
