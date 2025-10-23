package com.threego.algo.career.command.application.controller;

import com.threego.algo.career.command.application.dto.PostStatusUpdateRequest;
import com.threego.algo.career.command.application.service.AdminCareerCommandService;
import com.threego.algo.career.command.domain.aggregate.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Career Info - Admin Command",
        description = "관리자용 기업별 정보 공유 API (Command)"
)
@RestController
@RequestMapping("/admin/career-info")
public class AdminCareerCommandController {
    private final AdminCareerCommandService adminService;

    @Autowired
    public AdminCareerCommandController(@Qualifier("adminCareerCommandServiceImpl")AdminCareerCommandService adminService) {
        this.adminService = adminService;
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 상태 변경 (관리자)",
            description = "관리자가 기업별 정보 공유 게시물의 상태를 변경합니다."
    )
    @PutMapping("/posts/{postId}/status")
    public ResponseEntity<Void> updatePostStatus(
            @PathVariable int postId,
            @RequestBody PostStatusUpdateRequest request
    ) {
        adminService.updatePostStatus(postId, request.getStatus(), request.getRejectReason());
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "기업별 정보 공유 게시물 삭제 (관리자)",
            description = "관리자가 특정 게시물을 삭제합니다. soft delete로 visibility='N' 처리됩니다."
    )
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        adminService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 삭제 (관리자)",
            description = "관리자가 특정 댓글을 삭제합니다. soft delete로 visibility='N' 처리됩니다."
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        adminService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
