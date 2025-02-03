package com.example.todoapp.application;

import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.application.dto.CreateAuthorRequest;

public interface AuthorService {
    AuthorResponse createAuthor(CreateAuthorRequest createAuthorRequest);
}
