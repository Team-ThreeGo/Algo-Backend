package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class NotAuthorException extends BusinessException {
    public NotAuthorException() {
        super(ErrorCode.NOT_AUTHOR);
    }

    public NotAuthorException(final String message) {
        super(ErrorCode.NOT_AUTHOR, message);
    }

    public NotAuthorException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}