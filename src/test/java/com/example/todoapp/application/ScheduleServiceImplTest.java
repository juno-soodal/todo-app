package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.common.PageResponse;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.ScheduleSearchParam;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import com.example.todoapp.exception.NotFoundScheduleException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceImplTest {

    @Autowired
    ScheduleService scheduleService;

    @Test
    @DisplayName("일정 생성")
    @Transactional
    public void createSchedule() {
        ScheduleResponse schedule = scheduleService.createSchedule(3L, new CreateScheduleRequest("할일 테스트", "testasd"));
        Assertions.assertThat(schedule.getToDo()).isEqualTo("할일 테스트");
    }

    @Test
    @DisplayName("전체 일정 조회")
    public void getSchedules() {
        Long authorId = 3L;
        LocalDate modifiedAt = LocalDate.parse("2025-01-31");
        int page = 1;
        int size = 10;
        ScheduleSearchParam scheduleSearchParam = new ScheduleSearchParam(modifiedAt, page, size);
        PageResponse<ScheduleResponse> schedules = scheduleService.getSchedules(authorId, scheduleSearchParam);
        Assertions.assertThat(schedules.getData().get(0).getModifiedAt().toLocalDate()).isEqualTo(modifiedAt);
    }

    @Test
    @DisplayName("일정 단건 조회")
    public void getSchedule() {
        Long authorId = 3L;
        Long scheduleId = 133L;
        ScheduleResponse schedule = scheduleService.getSchedule(authorId, scheduleId);
        Assertions.assertThat(schedule.getId()).isEqualTo(133L);
    }

    @Test
    @DisplayName("일정 수정")
    @Transactional
    public void updateSchedule() {
        Long authorId = 3L;
        Long scheduleId = 133L;
        UpdateScheduleRequest updateScheduleRequest = new UpdateScheduleRequest("할일수정A", "testasd");
        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(authorId, scheduleId, updateScheduleRequest);
        Assertions.assertThat(scheduleResponse.getToDo()).isEqualTo("할일수정A");
    }

    @Test
    @DisplayName("일정 삭제 오류 반환")
    @Transactional
    public void deleteSchedule() {
        Long authorId = 3L;
        Long scheduleId = 500L;
       assertThrows(NotFoundScheduleException.class, ()-> scheduleService.deleteSchedule(authorId,scheduleId,"testasd"));
    }
}