package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

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
        boolean exists = authorRepository.existsByEmail("asd");
        assertThat(exists).isEqualTo(false);
    }

    @Test
    @Transactional
    @DisplayName("없는_사용자_아이디_조회")
    public void findIsNull() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "test56789@123.com", "testasd"));
        Author findAuthor = authorRepository.find(123L).orElse(null);
        assertThat(findAuthor).isNull();
    }

    @Test
    @Transactional
    @DisplayName("사용자_이메일_조회")
    public void findByEmailIsNull() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "test56789@123.com", "testasd"));
        boolean exists = authorRepository.existsByEmail(savedAuthor.getEmail());
        assertThat(exists).isEqualTo(true);
    }

    @Test
    @Transactional
    @DisplayName("사용자_아이디_조회")
    public void find() {
        Author savedAuthor = authorRepository.save(new Author("사용자1", "test56789@123.com", "testasd"));
        Author findAuthor = authorRepository.find(savedAuthor.getId()).orElse(null);
        assertThat(findAuthor.getAuthorName()).isEqualTo(savedAuthor.getAuthorName());
    }
}