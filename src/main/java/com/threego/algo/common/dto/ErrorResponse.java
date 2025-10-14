package com.threego.algo.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 에러 응답 DTO
 */
@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;

    public static ErrorResponse of(String code, String message, int status) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
