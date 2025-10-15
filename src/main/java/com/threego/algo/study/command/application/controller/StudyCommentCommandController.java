package com.threego.algo.study.command.application.controller;

import com.threego.algo.study.command.application.dto.create.StudyCommentCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyCommentUpdateDTO;
import com.threego.algo.study.command.application.service.StudyCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(
        name = "Study Comment - Member Command",
        description = "회원용 스터디 댓글 API (Command)"
)
public class StudyCommentCommandController {

    private final StudyCommentService studyCommentService;

    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    @Operation(summary = "댓글 등록", description = "스터디 그룹원이 게시물에 댓글을 등록합니다. 대댓글도 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터 또는 존재하지 않는 게시물"),
            @ApiResponse(responseCode = "403", description = "댓글 작성 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<String> createComment(
            @PathVariable int postId,
            @RequestBody StudyCommentCreateDTO commentDto,
            @RequestHeader("Member-Id") int memberId) {
        return studyCommentService.createComment(postId, memberId, commentDto);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글 작성자가 자신의 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터 또는 존재하지 않는 댓글"),
            @ApiResponse(responseCode = "403", description = "댓글 수정 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<String> updateComment(
            @PathVariable int commentId,
            @RequestBody StudyCommentUpdateDTO commentDto,
            @RequestHeader("Member-Id") int memberId) {
        return studyCommentService.updateComment(commentId, memberId, commentDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글 작성자가 자신의 댓글을 삭제합니다. (소프트 딜리트)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 댓글"),
            @ApiResponse(responseCode = "403", description = "댓글 삭제 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<String> deleteComment(
            @PathVariable int commentId,
            @RequestHeader("Member-Id") int memberId) {
        return studyCommentService.deleteComment(commentId, memberId);
    }
}