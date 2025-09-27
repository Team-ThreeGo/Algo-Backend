package com.threego.algo.coding.query.dao;

import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodingPostMapper {

    // 풀이 게시물 목록 조회 (최신순)
    List<CodingPostSummaryDTO> selectPostList(CodingPostSearchConditionDTO condition);

    // 풀이 게시물 목록 조회 (추천순)
    List<CodingPostSummaryDTO> selectPostByProblemIdOrderByLike(@Param("problemId") int problemId);

    // 풀이 게시물 상세 조회
    CodingPostDetailDTO selectPostDetail(@Param("postId") int postId);

    // 특정 게시물 댓글 목록 조회
    List<CodingPostCommentDTO> selectCommentsByPostId(@Param("postId") int postId);

    // 관리자용 전체 목록 조회
    List<CodingPostSummaryDTO> selectAdminPostList(CodingPostSearchConditionDTO condition);

    // 관리자용 게시물 상세 조회
    CodingPostDetailDTO selectAdminPostDetail(@Param("postId") int postId);

    // 관리자용 댓글 전체 조회
    List<CodingPostCommentDTO> selectAdminComments();
}
