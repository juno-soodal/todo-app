package com.example.todoapp.component;

import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.repository.AuthorRepository;
import com.example.todoapp.exception.NotFoundAuthorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorFinder {

    private final AuthorRepository authorRepository;

    public Author find(Long authorId) {
        return authorRepository.find(authorId).orElseThrow(NotFoundAuthorException::new);
    }
}
