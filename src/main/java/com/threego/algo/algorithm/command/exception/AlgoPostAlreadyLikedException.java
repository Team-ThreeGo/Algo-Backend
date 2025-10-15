package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class AlgoPostAlreadyLikedException extends BusinessException {
    public AlgoPostAlreadyLikedException() {
        super(ErrorCode.ALGO_POST_ALREADY_LIKED);
    }

    public AlgoPostAlreadyLikedException(final String message) {
        super(ErrorCode.ALGO_POST_ALREADY_LIKED, message);
    }
}