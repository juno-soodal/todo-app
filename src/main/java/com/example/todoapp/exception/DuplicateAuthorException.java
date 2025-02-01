package com.example.todoapp.exception;

public class DuplicateAuthorException extends  ToDoAppException{
    public DuplicateAuthorException() {
        super(ErrorCode.DUPLICATE_AUTHOR);
    }
}
