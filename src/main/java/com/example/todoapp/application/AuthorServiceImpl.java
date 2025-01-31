package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponse createAuthor(CreateAuthorRequest createAuthorRequest) {
        Author findAuthor = authorRepository.findByEmail(createAuthorRequest.getEmail());
        if (findAuthor != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 사용자입니다.");
        }
        Author newAuthor = authorRepository.save(new Author(createAuthorRequest.getAuthorName(), createAuthorRequest.getEmail(), createAuthorRequest.getPassword()));
        AuthorResponse authorResponse = new AuthorResponse(newAuthor.getId(), newAuthor.getAuthorName(), newAuthor.getEmail(), newAuthor.getCreatedAt(), newAuthor.getModifiedAt());
        return authorResponse;
    }
}
