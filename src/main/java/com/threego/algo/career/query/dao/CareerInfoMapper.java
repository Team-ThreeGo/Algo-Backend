package com.threego.algo.career.query.dao;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.career.query.dto.CareerPostSearchDTO;
import com.threego.algo.career.query.dto.CommentResponseDto;
import com.threego.algo.career.query.dto.PostDetailResponseDto;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CareerInfoMapper {
    // 기존 메서드 (하위 호환성 유지)
    List<PostSummaryResponseDto> selectPostList(
            @Param("visibility") String visibility,
            @Param("status") Status status,
            @Param("keyword") String keyword
    );

    // 새로운 페이지네이션 지원 메서드
    List<PostSummaryResponseDto> selectPostListWithPagination(CareerPostSearchDTO searchDTO);

    PostDetailResponseDto selectPost(
            @Param("postId") int postId,
            @Param("visibility") String visibility
    );

    List<CommentResponseDto> selectCommentsByPostId(
            @Param("postId") int postId
    );

    List<PostDetailResponseDto> selectComments();

    // 기존 메서드 (하위 호환성 유지)
    List<PostSummaryResponseDto> selectPostsByMember(@Param("memberId") int memberId);

    // 새로운 페이지네이션 지원 메서드
    List<PostSummaryResponseDto> selectPostsByMemberWithPagination(
            @Param("memberId") int memberId,
            @Param("searchDTO") CareerPostSearchDTO searchDTO
    );

    /* 설명. Career 게시물 전체 개수 조회 (검색 조건 포함) */
    long countCareerPosts(CareerPostSearchDTO searchDto);
}