package com.threego.algo.studyrecruit.command.application.controller;

import com.threego.algo.studyrecruit.command.application.service.StudyRecruitCommentService;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/study-recruit")
@RequiredArgsConstructor
@Tag(
        name = "Study Recruit - Admin Command",
        description = "관리자용 스터디 모집 API (Command)"
)
public class AdminStudyRecruitController {

    private final StudyRecruitPostService studyRecruitPostService;
    private final StudyRecruitCommentService studyRecruitCommentService;

    @Operation(
            summary = "모집 게시물 삭제 (관리자)",
            description = "관리자가 모집 게시물을 소프트 딜리트합니다. (VISIBILITY: Y → N)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모집 게시물 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (관리자 아님)"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deleteStudyRecruitPost(
            @Parameter(description = "모집 게시물 ID", required = true)
            @PathVariable int postId,

            @Parameter(description = "관리자 ID", required = true)
            @RequestHeader("Member-Id") int adminId) {

        return studyRecruitPostService.adminDeletePost(postId, adminId);
    }

    @Operation(
            summary = "모집 게시물 댓글 삭제 (관리자)",
            description = "관리자가 모집 게시물의 댓글을 소프트 딜리트합니다. (VISIBILITY: Y → N)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모집 댓글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (관리자 아님)"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteStudyRecruitComment(
            @Parameter(description = "모집 댓글 ID", required = true)
            @PathVariable int commentId,

            @Parameter(description = "관리자 ID", required = true)
            @RequestHeader("Member-Id") int adminId) {

        return studyRecruitCommentService.adminDeleteComment(commentId, adminId);
    }
}