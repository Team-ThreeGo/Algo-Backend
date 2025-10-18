package com.threego.algo.study.command.application.service;

import com.threego.algo.study.command.application.dto.create.StudyCommentCreateDTO;
import com.threego.algo.study.command.application.dto.create.StudyReportCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyCommentUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface StudyCommentService {

    /* 설명. 댓글 작성 */
    ResponseEntity<String> createComment(int postId, int memberId, StudyCommentCreateDTO request);

    /* 설명. 댓글 수정 */
    ResponseEntity<String> updateComment(int commentId, int memberId, StudyCommentUpdateDTO request);

    /* 설명. 댓글 삭제 */
    ResponseEntity<String> deleteComment(int commentId, int memberId);

    /* 설명. 관리자 댓글 삭제 */
    ResponseEntity<String> adminDeleteComment(int commentId, int adminId);
}