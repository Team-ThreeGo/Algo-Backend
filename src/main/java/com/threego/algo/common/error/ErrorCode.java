package com.threego.algo.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON01", "Entity not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON02", "Internal server error"),
    NOT_AUTHOR(HttpStatus.FORBIDDEN, "COMMON03", "Not author"),
    ALREADY_DELETED(HttpStatus.NOT_FOUND, "COMMON04", "Already deleted"),

    /* Algo */
    ALGO_ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "ALGO01", "Algo roadmap not found"),
    ALGO_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "ALGO02", "Algo post not found"),
    ALGO_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ALGO03", "Algo comment not found"),
    ALGO_QUIZ_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "ALGO04", "Algo quiz question not found"),
    DUPLICATED_TITLE(HttpStatus.BAD_REQUEST, "ALGO05", "It Is Duplicated title"),
    DUPLICATED_QUESTION(HttpStatus.BAD_REQUEST, "ALGO06", "It Is Duplicated question"),
    ALGO_CORRECT_QUIZ_HISTORY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ALGO07", "Algo quiz history already exists"),
    ALGO_POST_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "ALGO08", "Already liked post"),
    ALGO_QUIZ_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "ALGO09", "Algo quiz option not found"),

    /* Member */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER01", "Member not found"),;

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}