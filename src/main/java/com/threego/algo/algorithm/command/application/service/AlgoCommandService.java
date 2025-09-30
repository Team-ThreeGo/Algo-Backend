package com.threego.algo.algorithm.command.application.service;

import com.threego.algo.algorithm.command.application.dto.*;
import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;

public interface AlgoCommandService {
    AlgoRoadmap createAlgoRoadmap(final AlgoRoadmapRequestDTO request);

    AlgoRoadmap updateAlgoRoadmap(final int roadmapId, final AlgoRoadmapRequestDTO request) throws Exception;

    AlgoPostDetailResponseDTO createAlgoPost(final int memberId, final int roadmapId, final AlgoPostRequestDTO request) throws Exception;

    void deleteAlgoPost(final int postId) throws Exception;

    void createComment(final int memberId, final int postId,final AlgoCommentRequestDTO request) throws Exception;

    void updateComment(final int memberId, final int commentId, final AlgoCommentRequestDTO request) throws Exception;

    void deleteComment(final int memberId, final int commentId) throws Exception;

    void createCorrectQuizHistory(final int memberId, final int questionId) throws Exception;

    AlgoQuizQuestionResponseDTO createAlgoQuiz(final int postId, final AlgoQuizQuestionRequestDTO request) throws Exception;

    AlgoQuizQuestionResponseDTO updateAlgoQuiz(final int quizQuestionId, final UpdateAlgoQuizQuestionRequestDTO request) throws Exception;

    AlgoPostDetailResponseDTO updateAlgoPost(final int postId, final AlgoPostRequestDTO request) throws Exception;

    void deleteCommentForAdmin(final int commentId) throws Exception;

    void deleteAlgoQuizQuestion(int quizQuestionId) throws Exception;

    void createAlgoPostLikes(final int memberId, final int postId);
}