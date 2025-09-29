package com.threego.algo.career.command.application.controller;

import com.threego.algo.career.command.application.dto.CareerCommentRequest;
import com.threego.algo.career.command.application.dto.CareerPostCreateRequest;
import com.threego.algo.career.command.application.service.CareerCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Career Info - Member Command",
        description = "회원용 기업별 정보 공유 API (Command)"
)
@RestController
@RequestMapping("/career-info")
public class CareerCommandController {
   private final CareerCommandService service;

   @Autowired
    public CareerCommandController(@Qualifier("careerCommandServiceImpl") CareerCommandService service) {
        this.service = service;
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 등록",
            description = "회원이 기업별 정보 공유 게시판에 게시물을 등록합니다."
    )
    @PostMapping("/posts")
    public ResponseEntity<Integer> createPost(@RequestBody CareerPostCreateRequest request) {
        Integer postId = service.createPost(request);
        return ResponseEntity.ok(postId);
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 삭제 (회원)",
            description = "회원이 자신의 게시물을 삭제합니다. soft delete로 visibility를 'N'으로 변경합니다."
    )
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        service.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 게시물에 댓글 등록",
            description = "회원이 게시물에 댓글 또는 대댓글을 등록합니다."
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Integer> createComment(
            @PathVariable Integer postId,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestBody CareerCommentRequest request
    ) {
        Integer commentId = service.createComment(postId, parentId, request);
        return ResponseEntity.ok(commentId);
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 수정",
            description = "회원이 자신의 댓글을 수정합니다."
    )
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Integer commentId,
            @RequestBody CareerCommentRequest request
    ) {
        service.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 삭제 (회원)",
            description = "회원이 자신의 댓글을 삭제합니다. soft delete로 visibility='N'으로 변경합니다."
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        service.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
