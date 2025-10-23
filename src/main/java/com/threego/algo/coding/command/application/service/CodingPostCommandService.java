package com.threego.algo.coding.command.application.service;

import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostImageRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;

public interface CodingPostCommandService {

    int createPost(int memberId, int problemId, CodingPostRequestDTO dto);

    int addImage(int postId, CodingPostImageRequestDTO dto);

    CodingPost updatePost(int memberId, int postId, CodingPostRequestDTO dto);

    void softDeletePost(int memberId, int postId);

    int addComment(int memberId, int postId, Integer parentId, CodingCommentRequestDTO dto);

    void updateComment(int memberId, int commentId, CodingCommentRequestDTO dto);

    void softDeleteComment(int memberId, int commentId);

    int createCodingPostLikes(final int memberId, final int postId);
}