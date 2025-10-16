package com.threego.algo.studyrecruit.exception;

import com.threego.algo.common.error.exception.BusinessException;
import com.threego.algo.common.error.ErrorCode;

/**
 * StudyRecruit 도메인 예외 모음
 */
public class RecruitExceptions {

    /**
     * 모집글을 찾을 수 없을 때
     */
    public static class RecruitPostNotFoundException extends BusinessException {
        public RecruitPostNotFoundException() {
            super(ErrorCode.RECRUIT_POST_NOT_FOUND);
        }
    }

    /**
     * 모집글에 대한 권한이 없을 때
     */
    public static class RecruitPostNotAuthorizedException extends BusinessException {
        public RecruitPostNotAuthorizedException() {
            super(ErrorCode.RECRUIT_POST_NOT_AUTHORIZED);
        }

        public RecruitPostNotAuthorizedException(String customMessage) {
            super(ErrorCode.RECRUIT_POST_NOT_AUTHORIZED, customMessage);
        }
    }

    /**
     * 모집이 마감되지 않았을 때
     */
    public static class RecruitPostNotClosedException extends BusinessException {
        public RecruitPostNotClosedException() {
            super(ErrorCode.RECRUIT_POST_NOT_CLOSED);
        }
    }

    /**
     * 이미 마감된 모집글일 때
     */
    public static class RecruitPostAlreadyClosedException extends BusinessException {
        public RecruitPostAlreadyClosedException() {
            super(ErrorCode.RECRUIT_POST_ALREADY_CLOSED);
        }
    }

    /**
     * 이미 삭제된 모집글일 때
     */
    public static class RecruitPostAlreadyDeletedException extends BusinessException {
        public RecruitPostAlreadyDeletedException() {
            super(ErrorCode.RECRUIT_POST_ALREADY_DELETED);
        }
    }
}
