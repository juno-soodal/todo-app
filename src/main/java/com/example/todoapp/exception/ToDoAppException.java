package com.example.todoapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ToDoAppException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public ToDoAppException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.status = errorCode.getStatus();
        this.message = errorCode.getDefaultMessage();
    }
}
