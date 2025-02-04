package com.example.todoapp.presentation;

import com.example.todoapp.application.AuthorService;
import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.presentation.docs.AuthorControllerDocs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/authors")
public class AuthorController implements AuthorControllerDocs {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest createAuthorRequest) {
        AuthorResponse authorResponse = authorService.createAuthor(createAuthorRequest);
        return new ResponseEntity<>(authorResponse,HttpStatus.OK);
    }
}
