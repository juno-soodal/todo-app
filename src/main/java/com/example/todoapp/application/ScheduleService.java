package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.UpdateScheduleRequest;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<ScheduleResponse> getSchedules(Long authorId, LocalDate modifiedAt, int page, int size);

    ScheduleResponse getSchedule(Long authorId, Long scheduleId);

    ScheduleResponse updateSchedule(Long authorId, Long scheduleId, UpdateScheduleRequest updateScheduleRequest);

    void deleteSchedule(Long authorId, Long scheduleId, String password);

    ScheduleResponse createSchedule(Long authorId, CreateScheduleRequest createScheduleRequest);
}
