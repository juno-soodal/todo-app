package com.example.todoapp.application;

import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.common.PageResponse;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.ScheduleSearchParam;
import com.example.todoapp.application.dto.UpdateScheduleRequest;

public interface ScheduleService {

    PageResponse<ScheduleResponse> getSchedules(Long authorId, ScheduleSearchParam scheduleSearchParam);

    ScheduleResponse getSchedule(Long authorId, Long scheduleId);

    ScheduleResponse updateSchedule(Long authorId, Long scheduleId, UpdateScheduleRequest updateScheduleRequest);

    void deleteSchedule(Long authorId, Long scheduleId, String password);

    ScheduleResponse createSchedule(Long authorId, CreateScheduleRequest createScheduleRequest);
}
