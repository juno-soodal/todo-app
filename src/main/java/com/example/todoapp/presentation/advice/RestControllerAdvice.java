package com.example.todoapp.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> test(MethodArgumentNotValidException ex) {
        ex.getBody();
        return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
    }
}
