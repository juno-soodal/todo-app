package com.example.todoapp.exception;

public class InvalidPasswordException extends ToDoAppException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
