package com.example.todoapp.exception;

public class NotFoundAuthorException extends ToDoAppException{
    public NotFoundAuthorException() {
        super(ErrorCode.NOT_FOUND_AUTHOR);
    }
}
