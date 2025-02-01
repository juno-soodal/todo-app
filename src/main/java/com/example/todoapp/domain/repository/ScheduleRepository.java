package com.example.todoapp.domain.repository;

import com.example.todoapp.domain.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule newSchedule);

    List<Schedule> findAll(Long authorId, LocalDate modifiedAt, int page, int size);

    Optional<Schedule> find(Long authorId, Long scheduleId);

    int deleteSchedule(Long authorId, Long scheduleId);

    int updateSchedule(Schedule updateSchedule);

    int findTotalCount(Long id);
}
