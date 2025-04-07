package com.groomthon.univ.common.handler;

import com.groomthon.univ.common.exception.BaseException;
import com.groomthon.univ.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <h3>전역 예외 처리 핸들러</h3>
 * <p>모든 컨트롤러에서 발생하는 예외 처리</p>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 예외 에러 빌더
    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ApiResponse
                        .fail(status.value(), message)
                );
    }

    //
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(BaseException ex) {
        return buildErrorResponse(HttpStatus.valueOf(ex.getStatusCode()), ex.getResponseMessage());
    }
}
