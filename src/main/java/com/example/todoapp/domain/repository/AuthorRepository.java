package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;

public interface AuthorRepository {
    Author findByEmail(String email);

    Author save(Author author);

    Author findById(Long authorId);

}
