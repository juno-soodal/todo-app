package com.example.todoapp.application;

import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.component.AuthorValidator;
import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;

    @Override
    public AuthorResponse createAuthor(CreateAuthorRequest createAuthorRequest) {
        authorValidator.validateEmailNotExists(createAuthorRequest.getEmail());
        Author newAuthor = authorRepository.save(new Author(createAuthorRequest.getAuthorName(), createAuthorRequest.getEmail(), createAuthorRequest.getPassword()));
        return new AuthorResponse(newAuthor.getId(), newAuthor.getAuthorName(), newAuthor.getEmail(), newAuthor.getCreatedAt(), newAuthor.getModifiedAt());
    }
}
