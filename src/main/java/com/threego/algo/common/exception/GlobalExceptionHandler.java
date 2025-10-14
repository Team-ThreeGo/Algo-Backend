package com.threego.algo.common.exception;

import com.threego.algo.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BusinessException 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.warn("BusinessException: code={}, message={}", e.getErrorCode().getCode(), e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                e.getErrorCode().getCode(),
                e.getMessage(),
                e.getErrorCode().getStatus().value()
        );

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(errorResponse);
    }

    /**
     * IllegalArgumentException 처리 (기존 코드 호환성)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgumentException: {}", e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "INVALID_ARGUMENT",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * 예상하지 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected exception occurred", e);

        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
