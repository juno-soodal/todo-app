package com.example.todoapp.component;

import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public void validate(Author author, String password) {
        if(!author.isPassword(password)) {
            throw new InvalidPasswordException();
        }
    }
}
