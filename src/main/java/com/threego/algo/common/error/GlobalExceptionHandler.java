package com.threego.algo.common.error;

import com.threego.algo.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}