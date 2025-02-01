package com.example.todoapp.exception;

import lombok.Getter;

@Getter
public class ErrorReponse<T> {

    private final T error;


    public ErrorReponse(T error) {
        this.error = error;
    }
}
