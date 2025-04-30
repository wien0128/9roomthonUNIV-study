package com.groomthon.univ.common.handler;

import com.groomthon.univ.common.exception.BaseException;
import com.groomthon.univ.common.response.ApiResponse;
import com.groomthon.univ.common.response.ErrorStatus;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * <h3>전역 예외 처리 핸들러</h3>
 * <p>모든 컨트롤러에서 발생하는 예외 처리</p>
 */

// @RestControllerAdvice(annotations = {RestController.class}, basePackageClasses = {ArticleController.class})
@Hidden // @RestControllerAdvice-Swagger 충돌 방지
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 처리 (ex. 권한 없음)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse> handleCostumeException(BaseException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(ex.getStatusCode(), ex.getMessage()));
    }

    // 글로벌 예외 처리 (ex. 명시되어 있지 않은 예외 발생)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ErrorStatus.INTERNAL_SERVER_ERROR.getMessage()));
    }

    // 잘못된 인자 전달 시 발생 (ex. 숫자 필드에 문자열 입력)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    // 필수 요청 파라미터 누락 (ex. ID 없이 게시글 단건 조회)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParameterException(MissingServletRequestParameterException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(),
                        ErrorStatus.VALIDATION_MISSING_PARAMETER.getMessage() + ex.getParameterName()));
    }

    // DTO 유효성 검증 실패 (ex. @NotBlack 필드를 비운 채로 요청)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s: %s", error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(ErrorStatus.MISSING_REQUIRED_FIELD.getStatusCode(), errorMsg));
    }

    // DB에 존재하지 않는 리소스 접근/조작 시도 (ex. 없는 ID의 아티클 접근)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponse> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(HttpStatus.NOT_FOUND.value(),
                        ErrorStatus.RESOURCE_NOT_FOUND.getMessage()));
    }
}
