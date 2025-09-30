package com.threego.algo.coding.command.application.service;

import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostImageRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;

public interface CodingPostCommandService {

    int createPost(CodingPostRequestDTO dto);

    int addImage(int postId, CodingPostImageRequestDTO dto);

    CodingPost updatePost(int postId, CodingPostRequestDTO dto);

    void softDeletePost(int postId);

    int addComment(int postId, Integer parentId, CodingCommentRequestDTO dto);

    void updateComment(int commentId, CodingCommentRequestDTO dto);

    void softDeleteComment(int commentId);

    void createCodingPostLikes(final int memberId, final int postId);
}