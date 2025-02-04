package com.example.todoapp.presentation.docs;

import com.example.todoapp.application.common.PageResponse;
import com.example.todoapp.application.common.ResponseWrapper;
import com.example.todoapp.application.dto.CreateScheduleRequest;
import com.example.todoapp.application.dto.ScheduleResponse;
import com.example.todoapp.application.dto.ScheduleSearchParam;
import com.example.todoapp.application.dto.UpdateScheduleRequest;
import com.example.todoapp.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "일정 관리", description = "일정 관리 API 입니다.")
public interface ScheduleControllerDocs {

    @Operation(summary = "일정 생성", description = "작성자 ID와 패스워드를 검증한 후, 새로운 일정을 등록합니다.")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "일정 정보 저장 성공"),
            @ApiResponse(responseCode = "400", description = "일정 정보 저장 실패(잘못된 요청)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<ResponseWrapper<ScheduleResponse>> createSchedule(Long authorId,CreateScheduleRequest createScheduleRequest);

    @Operation(summary = "일정 전체 조회", description = "일정 전체 조회합니다.")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "일정 전체 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<PageResponse<ScheduleResponse>> getSchedules(Long authorId,ScheduleSearchParam scheduleSearchParam);

    @Operation(summary = "일정 단건 조회", description = "일정 단건을 조회합니다.")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "일정 단건 조회 성공"),
            @ApiResponse(responseCode = "400", description = "일정 단건 조회 실패(잘못된 요청)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<ResponseWrapper<ScheduleResponse>> getSchedule(Long authorId,Long scheduleId);

    @Operation(summary = "일정 수정", description = "일정 수정")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "일정 수정 성공"),
            @ApiResponse(responseCode = "400", description = "일정 수정 실패(잘못된 요청)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<ResponseWrapper<ScheduleResponse>> updateSchedule(Long authorId,Long scheduleId,UpdateScheduleRequest updateScheduleRequest);

    @Operation(summary = "일정 삭제", description = "일정 삭제")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "일정 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "일정 삭제 실패(잘못된 요청)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<String> deleteSchedule(Long authorId,Long scheduleId,String password);
}
