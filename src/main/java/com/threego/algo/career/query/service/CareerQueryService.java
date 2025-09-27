package com.threego.algo.career.query.service;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.career.query.dto.CommentResponseDto;
import com.threego.algo.career.query.dto.PostDetailResponseDto;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;

import java.util.List;

public interface CareerQueryService {

    List<PostSummaryResponseDto> findPostList(String visibility, Status status, String keyword);
    PostDetailResponseDto findPostForMember(int postId);
    PostDetailResponseDto findPostForAdmin(int postId);
    List<CommentResponseDto> findCommentsByPostId(int postId);
    List<PostDetailResponseDto> findCommentsForAdmin();
}
