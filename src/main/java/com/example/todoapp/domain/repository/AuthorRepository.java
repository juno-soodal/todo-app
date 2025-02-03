package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;

import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> find(Long authorId);

    boolean existsByEmail(String email);
}
