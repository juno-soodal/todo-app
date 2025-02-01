package com.example.todoapp.exception;

public class NotFoundScheduleException extends ToDoAppException{
    public NotFoundScheduleException() {
        super(ErrorCode.NOT_FOUND_SCHEDULE);
    }
}
