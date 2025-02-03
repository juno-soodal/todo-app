package com.example.todoapp.component;

import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.entity.Schedule;
import com.example.todoapp.domain.repository.ScheduleRepository;
import com.example.todoapp.exception.NotFoundScheduleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleWriter {

    private final ScheduleRepository scheduleRepository;

    public Schedule create(Author author, String toDo) {

       return scheduleRepository.save(new Schedule(author.getId(), toDo));
    }

    public void update(Schedule updateParam) {
        int updatedRow = scheduleRepository.updateSchedule(updateParam);
        if (updatedRow == 0) {
            throw new NotFoundScheduleException();
        }
    }

    public void delete(Long authorId, Long scheduleId) {
        int deletedRow = scheduleRepository.deleteSchedule(authorId, scheduleId);
        if (deletedRow == 0) {
            throw new NotFoundScheduleException();
        }
    }
}
