package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class AlgoPostAlreadyDeletedException extends BusinessException {
    public AlgoPostAlreadyDeletedException() {
        super(ErrorCode.ALREADY_DELETED);
    }

    public AlgoPostAlreadyDeletedException(final String message) {
        super(ErrorCode.ALREADY_DELETED, message);
    }
}