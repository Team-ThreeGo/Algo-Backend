package com.threego.algo.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON01", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON02", "Internal server error"),

    /* Algo */
    ALGO_ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "ALGO01", "Algo roadmap not found"),
    /* Member */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER01", "Member not found"),
    /* Report */
    REPORT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT02","Report category not found"),
    REPORT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT03","Report type not found"),
    REPORT_POST_OR_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT04","Post or Comment not found");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}