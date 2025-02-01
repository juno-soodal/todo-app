package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Author save(Author author) {
        String sql = """
                INSERT INTO AUTHOR
                (author_name, email, password, created_at, modified_at)
                VALUES(?, ?, ?, ?, ?)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, author.getAuthorName());
            preparedStatement.setString(2, author.getEmail());
            preparedStatement.setString(3, author.getPassword());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(author.getCreatedAt()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(author.getModifiedAt()));
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("failed generate keys");
        }
        Long key = keyHolder.getKey().longValue();
        return author.withId(key, author);
    }


    @Override
    public Optional<Author> findByEmail(String email) {
        String sql = "SELECT * FROM AUTHOR WHERE email = ?";
        return jdbcTemplate.query(sql, authorRowMapper(), email).stream().findFirst();

    }

    @Override
    public Optional<Author> findById(Long authorId) {
        String sql = "SELECT * FROM AUTHOR WHERE id = ?";
        return jdbcTemplate.query(sql, authorRowMapper(), authorId).stream().findFirst();
    }

    private RowMapper<Author> authorRowMapper() {
        return (rs, rowNum) -> new Author(
                rs.getLong("id"),
                rs.getString("author_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime());

    }
}
