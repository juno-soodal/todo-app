package com.example.todoapp.application;

import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.component.AuthorValidator;
import com.example.todoapp.exception.DuplicateAuthorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorValidator authorValidator;
    @Test
    @DisplayName("중복된 작성자 이메일 검증")
    public void validateAuthorEmailExists() {
        String email = "asd@123.com";
        Assertions.assertThrows(DuplicateAuthorException.class,()-> {
            authorValidator.validateEmailNotExists(email);
        });
    }

    @Test
    @DisplayName("작성자 생성 중복된 이메일 오류")
    public void createAuthor_ex() {
        String authorName = "신규 작성자";
        String email = "asd@123.com";
        String password = "test1234";
        CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest(authorName, email, password);
        Assertions.assertThrows(DuplicateAuthorException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authorService.createAuthor(createAuthorRequest);

            }
        });
    }

    @Test
    @Transactional
    @DisplayName("작성자 생성 정상")
    public void createAuthor() {
        String authorName = "신규 작성자";
        String email = "asda@123.com";
        String password = "test1234";
        AuthorResponse author = authorService.createAuthor(new CreateAuthorRequest(authorName,email,password));
        assertThat(author.getAuthorName()).isEqualTo(authorName);
    }
}