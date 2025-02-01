package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository{
    private final JdbcTemplate jdbcTemplate;
    private static Map<Long, Author> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();

    @Override
    public Author save(Author author) {
        String sql = """
                INSERT INTO AUTHOR
                (author_name, email, password, created_at, modified_at)
                VALUES(?, ?, ?, ?, ?)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,new String[]{"id"});
            preparedStatement.setString(1, author.getAuthorName());
            preparedStatement.setString(2, author.getEmail());
            preparedStatement.setString(3, author.getPassword());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(author.getCreatedAt()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(author.getModifiedAt()));
            return preparedStatement;
        }, keyHolder);
        author.setId(keyHolder.getKey().longValue());
        return author;
    }


    @Override
    public Optional<Author> findByEmail(String email) {
        String sql ="SELECT * FROM AUTHOR WHERE email = ?";
        return jdbcTemplate.query(sql, authorRowMapper(), email).stream().findFirst();

    }

    @Override
    public Optional<Author> findById(Long authorId) {
        String sql ="SELECT * FROM AUTHOR WHERE id = ?";
        return jdbcTemplate.query(sql, authorRowMapper(), authorId).stream().findFirst();
    }

    private RowMapper<Author> authorRowMapper() {
        return (rs, rowNum) -> {
            Author author = new Author(rs.getString("author_name"), rs.getString("email"), rs.getString("password"));
            author.setId(rs.getLong("id"));
            return author;
        };
    }
}
