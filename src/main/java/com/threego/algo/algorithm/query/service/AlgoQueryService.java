package com.threego.algo.algorithm.query.service;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import com.threego.algo.algorithm.query.dto.*;

import java.util.List;

public interface AlgoQueryService {
    List<AlgoRoadmap> findAllAlgoRoadmaps();

    List<AlgoPostSummaryResponseDTO> findAllAlgoPosts(final String keyword, final String visibility);

    List<AlgoPostSummaryResponseDTO> findAlgoPostsByRoadmapId(final int memberId, final int roadmapId,
                                                              final String keyword);

    List<AlgoPostSummaryResponseDTO> findAlgoPostsByRoadmapIdForAdmin(final int roadmapId, final String keyword,
                                                                      final String visibility);

    List<AlgoPostCommentDTO> findCommentsByPostId(final int postId);

    AlgoPostDetailResponseDTO findAlgoPostByPostId(final int memberId, final int postId);

    List<AlgoQuizResponseDTO> findQuizQuestionAndOptionByPostIdAndMemberId(final int postId, final Integer memberId);

    AlgoPostDetailResponseDTO findAlgoPostByPostIdForAdmin(final int postId);

    int countMemberCorrectAnswersInRoadmap(final int memberId, final int roadmapId);

    /* 설명. 알고리즘 로드맵 진행도 조회 */
    AlgoRoadmapProgressDTO findAlgoRoadmapProgress(int memberId);
}