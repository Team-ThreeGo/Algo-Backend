package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class DuplicatedQuestionException extends BusinessException {
    public DuplicatedQuestionException() {
        super(ErrorCode.DUPLICATED_QUESTION);
    }

    public DuplicatedQuestionException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }

    public DuplicatedQuestionException(final String message) {
        super(ErrorCode.DUPLICATED_QUESTION, message);
    }
}
