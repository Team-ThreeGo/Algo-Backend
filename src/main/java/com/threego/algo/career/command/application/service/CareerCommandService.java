package com.threego.algo.career.command.application.service;

import com.threego.algo.career.command.application.dto.CareerCommentRequest;
import com.threego.algo.career.command.application.dto.CareerPostCreateRequest;

public interface CareerCommandService {
    Integer createPost(CareerPostCreateRequest request, int memberId);
    void deletePost(int postId, int memberId);
    Integer createComment(int postId, Integer parentId, String content, int memberId);
    void updateComment(int commentId, CareerCommentRequest request, int memberId);
    void deleteComment(int commentId, int memberId);
    void createCareerPostLikes(final int memberId, final int postId);
}
