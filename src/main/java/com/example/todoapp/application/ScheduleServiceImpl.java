package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.entity.Schedule;
import com.example.todoapp.domain.repository.AuthorRepository;
import com.example.todoapp.domain.repository.ScheduleRepository;
import com.example.todoapp.exception.InvalidPasswordException;
import com.example.todoapp.exception.NotFoundAuthorException;
import com.example.todoapp.exception.NotFoundScheduleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;
    @Override
    public ScheduleResponse createSchedule(Long authorId, CreateScheduleRequest createScheduleRequest) {
        Author findAuthor = authorRepository.findById(authorId).orElseThrow(NotFoundAuthorException::new);
        if(!findAuthor.isPassword(createScheduleRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        Schedule newSchedule = new Schedule(findAuthor.getId(),createScheduleRequest.getToDo());
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        return new ScheduleResponse(savedSchedule.getId(),savedSchedule.getToDo(), findAuthor.getAuthorName() , savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
    }

    @Override
    public List<ScheduleResponse> getSchedules(Long authorId, LocalDate modifiedAt, int page, int size) {
        Author findAuthor = authorRepository.findById(authorId).orElseThrow(NotFoundAuthorException::new);


        int totalCount = scheduleRepository.findTotalCount(findAuthor.getId());
        double totalPages = Math.ceil((double) totalCount / size);
//        101 / 10  =10;
//        "page": page,
//                "size": size,
//                "totalPages": 4,
//                "totalElements": 100,
//        limit size offset page - 1 * size;
        List<Schedule> schedules = scheduleRepository.findAll(findAuthor.getId(), modifiedAt, page, size);
        return schedules.stream().map(schedule -> new ScheduleResponse(schedule.getId(), schedule.getToDo(), findAuthor.getAuthorName(), schedule.getCreatedAt(), schedule.getModifiedAt())).toList();
    }

    @Override
    public ScheduleResponse getSchedule(Long authorId, Long scheduleId) {

        Schedule findSchedule = scheduleRepository.find(authorId, scheduleId).orElseThrow(NotFoundScheduleException::new);
        Author findAuthor = authorRepository.findById(authorId).orElseThrow(NotFoundAuthorException::new);

        return new ScheduleResponse(findSchedule.getId(), findSchedule.getToDo(), findAuthor.getAuthorName(),findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Override
    public ScheduleResponse updateSchedule(Long authorId, Long scheduleId, UpdateScheduleRequest updateScheduleRequest) {
        Schedule findSchedule = scheduleRepository.find(authorId, scheduleId).orElseThrow(NotFoundScheduleException::new);
        Author findAuthor = authorRepository.findById(authorId).orElseThrow(NotFoundAuthorException::new);

        if (!findAuthor.isPassword(updateScheduleRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        log.info("findSchedule={}",findSchedule);
        findSchedule.updateSchedule(updateScheduleRequest.getToDo());
        int updatedRow = scheduleRepository.updateSchedule(findSchedule);
        if (updatedRow == 0) {
            throw new NotFoundScheduleException();
        }
        log.info("updatedSchedule={}",findSchedule);
        return new ScheduleResponse(findSchedule.getId(), findSchedule.getToDo(), findAuthor.getAuthorName(),findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Override
    public void deleteSchedule(Long authorId, Long scheduleId, String password) {
        Author findAuthor = authorRepository.findById(authorId).orElseThrow(NotFoundAuthorException::new);

        if (!findAuthor.isPassword(password)) {
            throw new InvalidPasswordException();
        }

        int deletedRow = scheduleRepository.deleteSchedule(authorId, scheduleId);
        if (deletedRow == 0) {
            throw new NotFoundScheduleException();
        }
    }
}
