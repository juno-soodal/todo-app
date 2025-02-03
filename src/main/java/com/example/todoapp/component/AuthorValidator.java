package com.example.todoapp.component;

import com.example.todoapp.domain.repository.AuthorRepository;
import com.example.todoapp.exception.DuplicateAuthorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository authorRepository;


    public void validateEmailNotExists(String email) {
        if(authorRepository.existsByEmail(email)) {
            throw new DuplicateAuthorException();
        }
    }
}
