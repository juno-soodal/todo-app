package com.example.todoapp.presentation;

import com.example.todoapp.application.ScheduleService;
import com.example.todoapp.application.common.ResponseWrapper;
import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.common.PageResponse;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.ScheduleSearchParam;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import com.example.todoapp.presentation.docs.ScheduleControllerDocs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/api/authors/{authorId}/schedules")
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDocs {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<ScheduleResponse>> createSchedule(@PathVariable Long authorId, @Valid @RequestBody CreateScheduleRequest createScheduleRequest) {
        return new ResponseEntity<>(new ResponseWrapper<>(scheduleService.createSchedule(authorId, createScheduleRequest)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ScheduleResponse>> getSchedules(@PathVariable Long authorId, @Valid @ModelAttribute ScheduleSearchParam scheduleSearchParam) {
        return new ResponseEntity<>(scheduleService.getSchedules(authorId, scheduleSearchParam), HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ResponseWrapper<ScheduleResponse>> getSchedule(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "scheduleId") Long scheduleId) {
        return new ResponseEntity<>(new ResponseWrapper<>(scheduleService.getSchedule(authorId, scheduleId)), HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ResponseWrapper<ScheduleResponse>> updateSchedule(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "scheduleId") Long scheduleId, @Valid @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        if (!StringUtils.hasText(updateScheduleRequest.getToDo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 데이터를 모두 입력해주세요");
        }

        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(authorId, scheduleId, updateScheduleRequest);
        return new ResponseEntity<>(new ResponseWrapper<ScheduleResponse>(scheduleResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable(value = "authorId") Long authorId, @PathVariable(value = "scheduleId") Long scheduleId, @RequestParam String password) {

        scheduleService.deleteSchedule(authorId, scheduleId, password);
        return new ResponseEntity<>("삭제가 완료되었습니다.",HttpStatus.OK);
    }
}
