package com.example.todoapp.component;

import com.example.todoapp.application.dto.ScheduleSearchParam;
import com.example.todoapp.domain.entity.Schedule;
import com.example.todoapp.domain.repository.ScheduleRepository;
import com.example.todoapp.exception.NotFoundScheduleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleFinder {
    private final ScheduleRepository scheduleRepository;


    public int findCount(Long authorId, ScheduleSearchParam scheduleSearchParam) {

       return  scheduleRepository.findCount(authorId, scheduleSearchParam.getModifiedAt());
    }

    public List<Schedule> findAll(Long authorId, LocalDate modifiedAt, int offset, int size) {
       return  scheduleRepository.findAll(authorId, modifiedAt, offset, size);
    }

    public Schedule find(Long authorId, Long scheduleId) {
        return scheduleRepository.find(authorId, scheduleId).orElseThrow(NotFoundScheduleException::new);
    }
}
