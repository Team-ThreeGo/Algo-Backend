package com.threego.algo.algorithm.query.service;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import com.threego.algo.algorithm.query.dto.AlgoPostCommentDTO;
import com.threego.algo.algorithm.query.dto.AlgoPostDetailResponseDTO;
import com.threego.algo.algorithm.query.dto.AlgoPostSummaryResponseDTO;
import com.threego.algo.algorithm.query.dto.AlgoQuizResponseDTO;

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

    List<AlgoQuizResponseDTO> findQuizQuestionAndOptionByPostId(final int postId);

    AlgoPostDetailResponseDTO findAlgoPostByPostIdForAdmin(final int postId);
}