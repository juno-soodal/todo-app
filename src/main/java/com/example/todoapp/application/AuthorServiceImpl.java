package com.example.todoapp.application;

import com.example.todoapp.application.dto.AuthorResponse;
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
    public AuthorResponse createAuthor(String authorName, String email,String password) {
        authorValidator.validateEmailNotExists(email);
        Author newAuthor = authorRepository.save(new Author(authorName,email,password));
        return new AuthorResponse(newAuthor.getId(), newAuthor.getAuthorName(), newAuthor.getEmail(), newAuthor.getCreatedAt(), newAuthor.getModifiedAt());
    }
}
