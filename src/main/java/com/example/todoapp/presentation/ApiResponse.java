package com.example.todoapp.presentation;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final T body;

    public ApiResponse(T body) {
        this.body = body;
    }
}
