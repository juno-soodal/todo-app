package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Schedule save(Schedule schedule) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new BeanPropertySqlParameterSource(schedule);
        String sql = """
                INSERT INTO
                SCHEDULE(author_id, to_do, created_at, modified_at)
                values(:authorId, :toDo, :createdAt, :modifiedAt)
                """;
        int insertedRow = jdbcTemplate.update(sql,param, keyHolder);
        long key = keyHolder.getKey().longValue();
        return schedule.withId(key, schedule);
    }

    @Override
    public List<Schedule> findAll(Long authorId, LocalDate modifiedAt, int offset, int size) {
        String sql = "SELECT * FROM SCHEDULE WHERE author_id = :authorId";

        Map<String, Object> paramMap = new HashMap<>();
        List<String> andConditions = new ArrayList<>();
        paramMap.put("authorId",authorId);
        paramMap.put("size",size);
        paramMap.put("offset", offset);
        if (modifiedAt != null) {
            paramMap.put("modifiedAt", Date.valueOf(modifiedAt));
            andConditions.add("DATE(modified_at) = :modifiedAt");
        }


        if (!andConditions.isEmpty()) {
            sql += " AND ";
            sql += String.join(" AND ", andConditions);
        }
        sql +=" ORDER BY modified_at DESC ";
        sql +=" LIMIT :size OFFSET :offset ";
        return jdbcTemplate.query(sql,paramMap, scheduleRowMapper());
    }


    @Override
    public Optional<Schedule> find(Long authorId, Long scheduleId) {
        String sql = """
                SELECT * FROM SCHEDULE
                WHERE id = :id and author_id = :authorId
        """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", scheduleId)
                .addValue("authorId", authorId);
        return jdbcTemplate.query(sql,param,scheduleRowMapper()).stream().findFirst();
    }

    @Override
    public int deleteSchedule(Long authorId, Long scheduleId) {
        String sql = """
                DELETE FROM SCHEDULE
                WHERE id = :scheduleId AND author_id = :authorId
        """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("scheduleId", scheduleId)
                .addValue("authorId", authorId);
        return jdbcTemplate.update(sql, param);
    }

    @Override
    public int updateSchedule(Schedule updateSchedule) {
        String sql = """
                UPDATE SCHEDULE
                SET to_do = :toDo, modified_at = :modifiedAt
                WHERE id = :id AND author_id = :authorId
        """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("toDo", updateSchedule.getToDo())
                .addValue("modifiedAt", updateSchedule.getModifiedAt())
                .addValue("id", updateSchedule.getId())
                .addValue("authorId", updateSchedule.getAuthorId());
        return jdbcTemplate.update(sql, param);
    }

    @Override
    public int findCount(Long authorId, LocalDate modifiedAt) {
        String sql = "SELECT count(*) FROM SCHEDULE WHERE author_id = :authorId";
        Map<String, Object> param = new HashMap<>();
        List<String> andConditions = new ArrayList<>();
        param.put("authorId",authorId);
        if (modifiedAt != null) {
            param.put("modifiedAt",Date.valueOf(modifiedAt));
            andConditions.add("DATE(modified_at) = :modifiedAt ");
        }
        if (!andConditions.isEmpty()) {
            sql += " AND ";
            sql += String.join(" AND ", andConditions);
        }
        return jdbcTemplate.queryForObject(sql,param,Integer.class);
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getString("to_do"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime());
    }

}
