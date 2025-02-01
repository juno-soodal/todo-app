package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.repository.AuthorRepository;
import com.example.todoapp.exception.DuplicateAuthorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponse createAuthor(CreateAuthorRequest createAuthorRequest) {
        Author other = null;
        Author findAuthor = authorRepository.findByEmail(createAuthorRequest.getEmail()).orElse(other);
        if (findAuthor != null) {
            throw new DuplicateAuthorException();
        }
        Author newAuthor = authorRepository.save(new Author(createAuthorRequest.getAuthorName(), createAuthorRequest.getEmail(), createAuthorRequest.getPassword()));
        AuthorResponse authorResponse = new AuthorResponse(newAuthor.getId(), newAuthor.getAuthorName(), newAuthor.getEmail(), newAuthor.getCreatedAt(), newAuthor.getModifiedAt());
        return authorResponse;
    }
}
