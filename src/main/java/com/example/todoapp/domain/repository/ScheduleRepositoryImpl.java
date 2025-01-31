package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Schedule save(Schedule schedule) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO
                SCHEDULE(author_id, to_do, created_at, modified_at)
                values(?, ?, ?, ?)
                """;
        int insertedRow = jdbcTemplate.update(con -> {
            PreparedStatement pstm = con.prepareStatement(sql, new String[]{"id"});
            pstm.setLong(1, schedule.getAuthorId());
            pstm.setString(2, schedule.getToDo());
            pstm.setTimestamp(3, Timestamp.valueOf(schedule.getCreatedAt()));
            pstm.setTimestamp(4, Timestamp.valueOf(schedule.getModifiedAt()));
            return pstm;
        }, keyHolder);
        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        schedule.setId(key);
        return schedule;
    }

    @Override
    public List<Schedule> findAll(Long authorId, LocalDate modifiedAt, int page, int size) {
        String sql = "SELECT * FROM SCHEDULE WHERE author_id = ?";

        List<String> params = new ArrayList<>();
        List<String> andConditions = new ArrayList<>();

        if (modifiedAt != null) {
            params.add(String.valueOf(modifiedAt));
            andConditions.add("DATE(modified_at) = ? ");
        }

        if (!andConditions.isEmpty()) {
            sql += " AND ";
            sql += String.join(" AND ", andConditions);
        }
        sql +=" ORDER BY modified_at DESC ";
        sql +=" LIMIT ? OFFSET ? ";
        int paramSize = params.size();
        return jdbcTemplate.query(sql, ps -> {
            int index = 1;
            ps.setLong(index++, authorId);
            for (String param : params) {
                ps.setString(index++, param);
            }
            ps.setInt(index++, size);
            ps.setInt(index, page * size);
        }, scheduleRowMapper());
    }


    @Override
    public Schedule find(Long authorId, Long scheduleId) {
        String sql = """
                SELECT * FROM SCHEDULE
                WHERE id = ? and author_id = ?
        """;
        return jdbcTemplate.query(sql, scheduleRowMapper(), scheduleId,authorId).stream().findAny().orElse(null);
    }

    @Override
    public int deleteSchedule(Long authorId, Long scheduleId) {
        String sql = """
                DELETE FROM SCHEDULE
                WHERE id = ? AND author_id = ?
        """;
        return jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, scheduleId);
            ps.setLong(2,authorId);
        });
    }

    @Override
    public int updateSchedule(Schedule updateSchedule) {
        String sql = """
                UPDATE SCHEDULE
                SET to_do = ?, modified_at = ?
                WHERE id= ? AND author_id = ?
        """;

        return jdbcTemplate.update(sql, ps -> {
            ps.setString(1, updateSchedule.getToDo());
            ps.setTimestamp(2, Timestamp.valueOf(updateSchedule.getModifiedAt()));
            ps.setLong(3, updateSchedule.getId());
            ps.setLong(4, updateSchedule.getAuthorId());
        });
    }

    @Override
    public int findTotalCount(Long authorId) {
        String sql = "SELECT count(*) FROM SCHEDULE WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, authorId);
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule(rs.getLong("author_id"), rs.getString("to_do"));
            schedule.setId(rs.getLong("id"));
            return schedule;
        };
    }

}
