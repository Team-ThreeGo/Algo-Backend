package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;

import java.util.List;

public interface CodingPostQueryService {

    // 회원용: 전체 문제별 게시물 목록 조회
    List<CodingPostSummaryDTO> findPostList(String keyword);



    // 회원용: 게시물 상세 조회
    CodingPostDetailDTO findPostDetail(int postId);

    // 회원용: 댓글 목록 조회
    List<CodingPostCommentDTO> findCommentsByPostId(int postId);

    // 관리자용: 문제별 전체 게시물 목록 조회
    List<CodingPostSummaryDTO> findPostListForAdmin(String visibility, String keyword);

    // 관리자용: 게시물 상세 조회
    CodingPostDetailDTO findPostDetailForAdmin(int postId);

    // 관리자용: 전체 댓글 조회
    List<CodingPostCommentDTO> findCommentsForAdmin();
}
