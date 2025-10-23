package com.threego.algo.coding.command.application.service;

import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingProblemRequestDTO;
import com.threego.algo.coding.command.domain.aggregate.CodingComment;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.coding.command.domain.aggregate.CodingProblem;

public interface AdminCodingPostCommandService {

    CodingPost updatePost(int memberId, int postId, CodingPostRequestDTO dto);

    void deletePost(int memberId, int postId);

    CodingComment updateComment(int memberId, int commentId, CodingCommentRequestDTO dto);

    void deleteComment(int memberId, int commentId);

    CodingProblem createProblem(int memberId, CodingProblemRequestDTO dto);

    CodingProblem updateProblem(int memberId, int problemId, CodingProblemRequestDTO dto);

    void deleteProblem(int memberId, int problemId);
}