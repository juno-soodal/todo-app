package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.application.dto.AuthorResponse;

public interface AuthorService {
    AuthorResponse createAuthor(String authorName, String email, String password);
}
