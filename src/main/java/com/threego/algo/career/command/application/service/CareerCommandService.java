package com.threego.algo.career.command.application.service;

import com.threego.algo.career.command.application.dto.CareerCommentRequest;
import com.threego.algo.career.command.application.dto.CareerPostCreateRequest;

public interface CareerCommandService {
    Integer createPost(CareerPostCreateRequest request);
    void deletePost(int postId);
    Integer createComment(int postId, Integer parentId, CareerCommentRequest request);
    void updateComment(int commentId, CareerCommentRequest request);
    void deleteComment(int commentId);
    void createCareerPostLikes(final int memberId, final int postId);
}
