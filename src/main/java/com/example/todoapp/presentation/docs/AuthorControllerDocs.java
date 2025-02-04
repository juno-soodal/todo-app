package com.example.todoapp.presentation.docs;

import com.example.todoapp.application.dto.AuthorResponse;
import com.example.todoapp.application.dto.CreateAuthorRequest;
import com.example.todoapp.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "작성자 관리", description = "작성자 관리 API 입니다.")
public interface AuthorControllerDocs {

    @Operation(summary = "작성자 생성", description = "작성자 정보를 저장합니다.")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "작성자 정보 저장 성공"),
            @ApiResponse(responseCode = "400", description = "작성자 정보 저장 실패(중복된 이메일)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<AuthorResponse> createAuthor(CreateAuthorRequest createAuthorRequest);
}
