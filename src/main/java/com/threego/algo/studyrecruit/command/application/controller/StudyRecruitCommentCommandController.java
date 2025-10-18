package com.threego.algo.studyrecruit.command.application.controller;


import com.threego.algo.common.auth.LoginMember;
import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitCommentCreateDTO;
import com.threego.algo.studyrecruit.command.application.dto.update.StudyRecruitCommentUpdateDTO;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitCommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study-recruit/comments")
@RequiredArgsConstructor
@Tag(
        name = "Study Recruit Comment - Member Command",
        description = "회원용 스터디 모집 댓글 API (Command)"
)
public class StudyRecruitCommentCommandController {

    private final StudyRecruitCommentService studyRecruitCommentService;

    @Operation(summary = "댓글 등록", description = "모집글에 댓글을 등록합니다.")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createComment(
            @PathVariable int postId,
            @LoginMember int memberId,
            @Valid @RequestBody StudyRecruitCommentCreateDTO request) {
        return studyRecruitCommentService.createComment(postId, memberId, request);
    }

    @Operation(summary = "댓글 수정", description = "작성자가 자신의 댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable int commentId,
            @LoginMember int memberId,
            @Valid @RequestBody StudyRecruitCommentUpdateDTO request) {
        return studyRecruitCommentService.updateComment(commentId, memberId, request);
    }

    @Operation(summary = "댓글 삭제", description = "작성자가 자신의 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable int commentId,
            @LoginMember int memberId) {
        return studyRecruitCommentService.deleteComment(commentId, memberId);
    }

}
