package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import com.example.todoapp.domain.entity.Author;
import com.example.todoapp.domain.entity.Schedule;
import com.example.todoapp.domain.repository.AuthorRepository;
import com.example.todoapp.domain.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;
    @Override
    public ScheduleResponse createSchedule(Long authorId, CreateScheduleRequest createScheduleRequest) {
        Author findAuthor = authorRepository.findById(authorId);
        if (findAuthor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 작성자 정보를 입력하세요");
        }
        if(!findAuthor.isPassword(createScheduleRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지않는 값입니다.");
        }
        Schedule newSchedule = new Schedule(findAuthor.getId(),createScheduleRequest.getToDo());
        Schedule savedSchedule = scheduleRepository.save(newSchedule);

        return new ScheduleResponse(savedSchedule.getId(),savedSchedule.getToDo(), findAuthor.getAuthorName() , savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
    }

    @Override
    public List<ScheduleResponse> getSchedules(Long authorId, LocalDate modifiedAt, int page, int size) {
        Author findAuthor = authorRepository.findById(authorId);
        if (findAuthor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 사용자입니다.");
        }

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

        Schedule findSchedule = scheduleRepository.find(authorId, scheduleId);
        if (findSchedule == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지않는 일정입니다.");
        }
        Author findAuthor = authorRepository.findById(findSchedule.getAuthorId());

        return new ScheduleResponse(findSchedule.getId(), findSchedule.getToDo(), findAuthor.getAuthorName(),findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Override
    public ScheduleResponse updateSchedule(Long authorId, Long scheduleId, UpdateScheduleRequest updateScheduleRequest) {
        Schedule findSchedule = scheduleRepository.find(authorId, scheduleId);
        if (findSchedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지않는 일정입니다.");
        }
        Author findAuthor = authorRepository.findById(findSchedule.getAuthorId());
        if (findAuthor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지않는 값입니다.");
        }

        if (!findAuthor.isPassword(updateScheduleRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지않는 값입니다.");
        }
        findSchedule.updateSchedule(updateScheduleRequest.getToDo());
        return new ScheduleResponse(findSchedule.getId(), findSchedule.getToDo(), findAuthor.getAuthorName(),findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Override
    public void deleteSchedule(Long authorId, Long scheduleId, String password) {
        Schedule findSchedule = scheduleRepository.find(authorId, scheduleId);
        if (findSchedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지않는 일정입니다.");
        }

        Author findAuthor = authorRepository.findById(findSchedule.getAuthorId());
        if (findAuthor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지않는 값입니다.");
        }

        if (!findAuthor.isPassword(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지않는 값입니다.");
        }

        int deletedRow = scheduleRepository.deleteSchedule(authorId, scheduleId);
        if (deletedRow != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정삭제 실패했습니다.");
        }
    }
}
