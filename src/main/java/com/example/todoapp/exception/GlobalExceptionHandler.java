package com.example.todoapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTodoAppException(ToDoAppException e, HttpServletRequest request) {
        logWarn(e,request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(response), e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {

        logWarn(e,request);
        Map<String, Object> response = new HashMap<>();
        List<String> errors = e.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
        response.put("message", errors);
        return new ResponseEntity<>(new ErrorResponse(response), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        logWarn(e, request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getName() + " 정확한 형식을 입력해주세요");
        return new ResponseEntity<>(new ErrorResponse(response), HttpStatus.BAD_REQUEST);
    }

    private void logWarn(Exception e, HttpServletRequest request) {
        log.warn("[Exception] {} | Message: {} | Cause: {} | Path: {}",
                e.getClass().getSimpleName(),
                e.getMessage(),
                e.getCause() != null ? e.getCause().toString() : "NoCause",
                request.getRequestURI());
    }
}
