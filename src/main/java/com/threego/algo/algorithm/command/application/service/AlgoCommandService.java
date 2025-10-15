package com.threego.algo.algorithm.command.application.service;

import com.threego.algo.algorithm.command.application.dto.*;
import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;

public interface AlgoCommandService {
    AlgoRoadmap createAlgoRoadmap(final AlgoRoadmapRequestDTO request);

    AlgoRoadmap updateAlgoRoadmap(final int roadmapId, final AlgoRoadmapRequestDTO request);

    AlgoPostDetailResponseDTO createAlgoPost(final int memberId, final int roadmapId, final AlgoPostRequestDTO request);

    void deleteAlgoPost(final int postId);

    void createComment(final int memberId, final int postId, final AlgoCommentRequestDTO request) ;

    void updateComment(final int memberId, final int commentId, final AlgoCommentRequestDTO request) ;

    void deleteComment(final int memberId, final int commentId) ;

    void createCorrectQuizHistory(final int memberId, final int questionId);

    AlgoQuizQuestionResponseDTO createAlgoQuiz(final int postId, final AlgoQuizQuestionRequestDTO request);

    AlgoQuizQuestionResponseDTO updateAlgoQuiz(final int quizQuestionId, final UpdateAlgoQuizQuestionRequestDTO request);

    AlgoPostDetailResponseDTO updateAlgoPost(final int postId, final AlgoPostRequestDTO request);

    void deleteCommentForAdmin(final int commentId);

    void deleteAlgoQuizQuestion(int quizQuestionId);

    void createAlgoPostLikes(final int memberId, final int postId);
}