package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Author save(Author author) {
        String sql = """
                INSERT INTO AUTHOR
                (author_name, email, password, created_at, modified_at)
                VALUES(:authorName, :email, :password, :createdAt, :modifiedAt)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new BeanPropertySqlParameterSource(author);
        jdbcTemplate.update(sql,param,keyHolder);
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("failed generate keys");
        }
        Long key = keyHolder.getKey().longValue();
        return author.withId(key, author);
    }

    @Override
    public Optional<Author> find(Long authorId) {
        String sql = "SELECT * FROM AUTHOR WHERE id = :authorId";

        return jdbcTemplate.query(sql, Map.of("authorId",authorId), authorRowMapper()).stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS(SELECT 1 FROM AUTHOR WHERE email = :email)";
        return jdbcTemplate.queryForObject(sql,Map.of("email",email), Boolean.class);
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
