package com.example.todoapp.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    private final Map<String, Object> error;


    public ErrorResponse(Map<String, Object> error) {
        this.error = error;
    }
}
