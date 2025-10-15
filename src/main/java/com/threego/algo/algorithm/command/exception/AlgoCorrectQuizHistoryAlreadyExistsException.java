package com.threego.algo.algorithm.command.exception;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;

public class AlgoCorrectQuizHistoryAlreadyExistsException extends BusinessException {
    public AlgoCorrectQuizHistoryAlreadyExistsException() {
        super(ErrorCode.ALGO_CORRECT_QUIZ_HISTORY_ALREADY_EXISTS);
    }

    public AlgoCorrectQuizHistoryAlreadyExistsException(final String message) {
        super(ErrorCode.ALGO_CORRECT_QUIZ_HISTORY_ALREADY_EXISTS, message);
    }
}