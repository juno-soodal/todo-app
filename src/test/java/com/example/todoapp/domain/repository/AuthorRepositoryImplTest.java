package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorRepositoryImplTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @Transactional
    public void save() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "asd1234@123.com", "testasd"));
        assertThat(savedAuthor.getAuthorName()).isEqualTo("사용자1");

    }

    @Test
    @DisplayName("없는_사용자_이메일_조회")
    public void findByEmailNotFound() {
        Author findAuthor = authorRepository.findByEmail("asd").orElse(null);
        assertThat(findAuthor).isNull();
    }

    @Test
    @Transactional
    @DisplayName("없는_사용자_아이디_조회")
    public void findByIdIsNull() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "asd1234@123.com", "testasd"));
        Author findAuthor = authorRepository.findById(123L).orElse(null);
        assertThat(findAuthor).isNull();
    }

    @Test
    @DisplayName("사용자_이메일_조회")
    public void findByEmailIsNull() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "asd1234@123.com", "testasd"));
        Author findAuthor = authorRepository.findByEmail(savedAuthor.getEmail()).orElse(null);
        assertThat(findAuthor.getAuthorName()).isEqualTo(savedAuthor.getAuthorName());
    }

    @Test
    @Transactional
    @DisplayName("사용자_아이디_조회")
    public void findById() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "asd1234@123.com", "testasd"));
        Author findAuthor = authorRepository.findById(savedAuthor.getId()).orElse(null);
        assertThat(findAuthor.getAuthorName()).isEqualTo(savedAuthor.getAuthorName());
    }
}