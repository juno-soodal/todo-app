package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.domain.repository.AuthorRepository;
import com.example.todoapp.domain.repository.ScheduleRepository;
import com.example.todoapp.domain.repository.ScheduleRepositoryImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class ScheduleServiceImplTest {

    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Validator validator;

    @BeforeEach
    void setUp() {
//        this.scheduleRepository = new MemoryScheduleRepository();
        this.scheduleRepository = new ScheduleRepositoryImpl(jdbcTemplate);


    }

    @Test
    @DisplayName("일정생성")
    @Transactional
    @Commit
    public void createSchedule() {

        AuthorResponse  authorResponse= prepareAuthorResponse();

        CreateScheduleRequest createScheduleRequest = new CreateScheduleRequest("할일 테스트", "test1234");

        ScheduleResponse createdScheduleResponse = scheduleService.createSchedule(authorResponse.getId(), createScheduleRequest);

        assertThat(createdScheduleResponse.getAuthorName()).isEqualTo(authorResponse.getAuthorName());
    }

    private AuthorResponse prepareAuthorResponse() {
        CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest("홍길동", "test123@asc.com", "test1234");

        return authorService.createAuthor(createAuthorRequest);

    }

    @Test
    @DisplayName("일정목록_조회")
    public void getSchedules() {
        AuthorResponse  authorResponse= prepareAuthorResponse();
        List<ScheduleResponse> schedules = scheduleService.getSchedules(authorResponse.getId(), LocalDate.parse("2025-01-31"));

        assertThat(schedules.size()).isEqualTo(1);

    }
}