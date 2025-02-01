package com.example.todoapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    DUPLICATE_AUTHOR("중복된 작성자입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_AUTHOR("해당 ID에 대한 작성자가 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_SCHEDULE("해당 ID에 대한 일정이 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("입력한 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(String defaultMessage,HttpStatus status) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
}
