package com.example.todoapp.presentation;

import com.example.todoapp.application.ScheduleService;
import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/authors/{authorId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(@PathVariable Long authorId, @Valid @RequestBody CreateScheduleRequest createScheduleRequest) {
        ScheduleResponse scheduleResponse = scheduleService.createSchedule(authorId, createScheduleRequest);
        ApiResponse<ScheduleResponse> apiResponse = new ApiResponse<>(scheduleResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getSchedules(@PathVariable Long authorId, @RequestParam(required = false) LocalDate modifiedAt,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "페이지 번호는 0 이상이어야 합니다.");
        }
        List<ScheduleResponse> scheduleResponseList = scheduleService.getSchedules(authorId, modifiedAt, page, size);
        ApiResponse<List<ScheduleResponse>> apiResponse = new ApiResponse<>(scheduleResponseList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> getSchedule(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "scheduleId") Long scheduleId) {
        ScheduleResponse scheduleResponse = scheduleService.getSchedule(authorId, scheduleId);
        ApiResponse<ScheduleResponse> apiResponse = new ApiResponse<>(scheduleResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "scheduleId") Long scheduleId, @Valid @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        if (!StringUtils.hasText(updateScheduleRequest.getToDo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 데이터를 모두 입력해주세요");
        }

        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(authorId, scheduleId, updateScheduleRequest);
        ApiResponse<ScheduleResponse> apiResponse = new ApiResponse<>(scheduleResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "scheduleId") Long scheduleId, @RequestParam String password) {

        scheduleService.deleteSchedule(authorId, scheduleId, password);
        return new ResponseEntity<>("삭제가 완료되었습니다.",HttpStatus.OK);
    }
}
