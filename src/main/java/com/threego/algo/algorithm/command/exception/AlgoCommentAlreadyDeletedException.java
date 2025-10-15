package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class AlgoCommentAlreadyDeletedException extends BusinessException {
    public AlgoCommentAlreadyDeletedException() {
        super(ErrorCode.ALREADY_DELETED);
    }

    public AlgoCommentAlreadyDeletedException(final String message) {
        super(ErrorCode.ALREADY_DELETED, message);
    }
}