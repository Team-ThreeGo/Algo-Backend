package com.threego.algo.career.query.dao;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.career.query.dto.CommentResponseDto;
import com.threego.algo.career.query.dto.PostDetailResponseDto;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CareerInfoMapper {
    List<PostSummaryResponseDto> selectPostList(
            @Param("visibility") String visibility,
            @Param("status") Status status,
            @Param("keyword") String keyword
    );

    PostDetailResponseDto selectPost(
            @Param("postId") int postId,
            @Param("visibility") String visibility
    );

    List<CommentResponseDto> selectCommentsByPostId(
            @Param("postId") int postId
    );

    List<PostDetailResponseDto> selectComments();
}
