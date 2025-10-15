package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class DuplicatedTitleException extends BusinessException {
    public DuplicatedTitleException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicatedTitleException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}