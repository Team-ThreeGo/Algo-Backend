package com.threego.algo.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


/* 설명. 에러 코드 정의 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /* Common */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C003", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C004", "권한이 없습니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C005", "Entity not found"),

    /* Member */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "회원을 찾을 수 없습니다."),

    /* Study */
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "스터디를 찾을 수 없습니다."),
    STUDY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "S002", "이미 스터디가 생성된 모집글입니다."),
    STUDY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "S003", "스터디 멤버를 찾을 수 없습니다."),
    STUDY_NOT_LEADER(HttpStatus.FORBIDDEN, "S004", "그룹장만 수행할 수 있는 작업입니다."),
    STUDY_HAS_ACTIVE_MEMBERS(HttpStatus.CONFLICT, "S005", "활동 중인 그룹원이 있는 상태에서는 그룹을 삭제할 수 없습니다."),
    STUDY_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "S006", "스터디 게시물을 찾을 수 없습니다."),
    STUDY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "S007", "스터디 접근 권한이 없습니다."),
  
    /* StudyRecruit */
    RECRUIT_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "SR001", "모집글을 찾을 수 없습니다."),
    RECRUIT_POST_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "SR002", "모집글에 대한 권한이 없습니다."),
    RECRUIT_POST_NOT_CLOSED(HttpStatus.BAD_REQUEST, "SR003", "모집이 마감되지 않은 게시물입니다."),
    RECRUIT_POST_ALREADY_CLOSED(HttpStatus.BAD_REQUEST, "SR004", "이미 마감된 모집글입니다."),
    RECRUIT_POST_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "SR005", "이미 삭제된 게시물입니다."),

    /* Algo */
    ALGO_ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "ALGO01", "Algo roadmap not found"),

    /* Report */
    REPORT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT02","신고 카테고리를 찾을 수 없습니다."),
    REPORT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT03","신고 타입을 찾을 수 없습니다."),
    REPORT_POST_OR_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT04","게시글 또는 댓글이 존재하지 않습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}