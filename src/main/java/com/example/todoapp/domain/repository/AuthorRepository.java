package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findByEmail(String email);

    Author save(Author author);

    Optional<Author> findById(Long authorId);

}
