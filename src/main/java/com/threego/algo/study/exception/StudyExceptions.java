package com.threego.algo.study.exception;

import com.threego.algo.common.error.exception.BusinessException;
import com.threego.algo.common.error.ErrorCode;

/* 설명. Study 도메인 예외 모음 */
public class StudyExceptions {

    /* 설명. 스터디를 찾을 수 없을 때 */
    public static class StudyNotFoundException extends BusinessException {
        public StudyNotFoundException() {
            super(ErrorCode.STUDY_NOT_FOUND);
        }

        public StudyNotFoundException(String customMessage) {
            super(ErrorCode.STUDY_NOT_FOUND, customMessage);
        }
    }

    /* 설명. 이미 스터디가 생성된 경우 */
    public static class StudyAlreadyExistsException extends BusinessException {
        public StudyAlreadyExistsException() {
            super(ErrorCode.STUDY_ALREADY_EXISTS);
        }
    }

    /* 설명. 스터디 멤버를 찾을 수 없을 때 */
    public static class StudyMemberNotFoundException extends BusinessException {
        public StudyMemberNotFoundException() {
            super(ErrorCode.STUDY_MEMBER_NOT_FOUND);
        }
    }

    /* 설명. 그룹장 권한이 없을 때 */
    public static class StudyNotLeaderException extends BusinessException {
        public StudyNotLeaderException() {
            super(ErrorCode.STUDY_NOT_LEADER);
        }

        public StudyNotLeaderException(String customMessage) {
            super(ErrorCode.STUDY_NOT_LEADER, customMessage);
        }
    }

    /* 설명. 활동 중인 멤버가 있어서 스터디를 삭제할 수 없을 때 */
    public static class StudyHasActiveMembersException extends BusinessException {
        public StudyHasActiveMembersException() {
            super(ErrorCode.STUDY_HAS_ACTIVE_MEMBERS);
        }
    }
}
