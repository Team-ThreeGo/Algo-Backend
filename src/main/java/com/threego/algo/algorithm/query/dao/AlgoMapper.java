package com.threego.algo.algorithm.query.dao;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import com.threego.algo.algorithm.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlgoMapper {
    List<AlgoRoadmap> selectAllAlgoRoadmaps();

    List<AlgoPostSummaryResponseDTO> selectAlgoPosts(@Param("memberId") final Integer memberId,
                                                     @Param("roadmapId") final Integer roadmapId,
                                                     @Param("keyword") final String keyword,
                                                     @Param("visibility") final String visibility);

    List<AlgoMemberSolvedQuizResponseDTO> selectSolvedQuizzesByMemberIdAndRoadmapIds(@Param("memberId") final Integer memberId,
                                                                                     @Param("roadmapId") final Integer roadmapId);

    List<AlgoPostCommentDTO> selectCommentsByPostId(final int postId);

    AlgoPostDetailResponseDTO selectAlgoPostByPostId(@Param("memberId") final Integer memberId,
                                                     @Param("postId") final Integer postId);

    List<String> selectAlgoImagesByPostId(@Param("postId") Integer postId);

    List<AlgoQuizResponseDTO> selectQuizQuestionAndOptionByPostIdAndMemberId(@Param("postId") final int postId,
                                                                           @Param("memberId") final Integer memberId);

    int countMemberCorrectAnswersInRoadmap(@Param("memberId") final int memberId, @Param("roadmapId") final int roadmapId);

    /* 설명. 알고리즘 로드맵 진행도 조회 */
    AlgoRoadmapProgressDTO selectAlgoRoadmapProgress(@Param("memberId") int memberId);
}