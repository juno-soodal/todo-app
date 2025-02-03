package com.example.todoapp.application.common;

import lombok.Getter;

@Getter
public class ResponseWrapper<T> {

    private final T data;

    public ResponseWrapper(T data) {
        this.data = data;
    }
}
