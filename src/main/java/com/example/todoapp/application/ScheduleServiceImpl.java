package com.example.todoapp.application;

import com.example.todoapp.application.common.Pagination;
import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.common.PageResponse;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.ScheduleSearchParam;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import com.example.todoapp.component.PasswordValidator;
import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.entity.Schedule;
import com.example.todoapp.component.AuthorFinder;
import com.example.todoapp.component.ScheduleWriter;
import com.example.todoapp.component.ScheduleFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final AuthorFinder authorFinder;
    private final PasswordValidator passwordValidator;
    private final ScheduleWriter scheduleWriter;
    private final ScheduleFinder scheduleFinder;

    @Override
    public ScheduleResponse createSchedule(Long authorId, CreateScheduleRequest createScheduleRequest) {
        Author author = authorFinder.find(authorId);
        passwordValidator.validate(author, createScheduleRequest.getPassword());
        Schedule schedule = scheduleWriter.create(author, createScheduleRequest.getToDo());

        return new ScheduleResponse(
                schedule.getId(),
                schedule.getToDo(),
                author.getAuthorName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Override
    public PageResponse<ScheduleResponse> getSchedules(Long authorId, ScheduleSearchParam scheduleSearchParam) {
        Author author = authorFinder.find(authorId);
        int count = scheduleFinder.findCount(author.getId(), scheduleSearchParam);
        Pagination pagination = new Pagination(scheduleSearchParam.getPage(), scheduleSearchParam.getSize(),count);
        List<Schedule> schedules = scheduleFinder.findAll(author.getId(), scheduleSearchParam.getModifiedAt(), pagination.getOffset(), scheduleSearchParam.getSize());
        List<ScheduleResponse> scheduleResponses = schedules.stream()
                .map(schedule -> new ScheduleResponse(
                        schedule.getId(),
                        schedule.getToDo(),
                        author.getAuthorName(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .toList();
        return new PageResponse<>(pagination.getPage(), pagination.getSize(),pagination.getTotalPages(), pagination.getTotalElements(), scheduleResponses);
    }

    @Override
    public ScheduleResponse getSchedule(Long authorId, Long scheduleId) {
        Schedule schedule = scheduleFinder.find(authorId, scheduleId);
        Author author = authorFinder.find(authorId);

        return new ScheduleResponse(
                schedule.getId(),
                schedule.getToDo(),
                author.getAuthorName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Override
    public ScheduleResponse updateSchedule(Long authorId, Long scheduleId, UpdateScheduleRequest updateScheduleRequest) {
        Schedule schedule = scheduleFinder.find(authorId, scheduleId);
        Author author = authorFinder.find(authorId);
        passwordValidator.validate(author, updateScheduleRequest.getPassword());
        schedule.updateSchedule(updateScheduleRequest.getToDo());
        scheduleWriter.update(schedule);

        return new ScheduleResponse(
                schedule.getId(),
                schedule.getToDo(),
                author.getAuthorName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Override
    public void deleteSchedule(Long authorId, Long scheduleId, String password) {
        Author author = authorFinder.find(authorId);
        passwordValidator.validate(author, password);
        scheduleWriter.delete(authorId, scheduleId);
    }
}
