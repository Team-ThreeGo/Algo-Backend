package com.threego.algo.coding.command.application.controller;

import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingProblemRequestDTO;
import com.threego.algo.coding.command.application.service.AdminCodingPostCommandService;
import com.threego.algo.coding.command.domain.aggregate.*;
import com.threego.algo.common.auth.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Coding - Admin Command",
        description = "관리자용 코딩 게시물 및 문제 API (Command)"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCodingController {

    @Qualifier("adminCodingPostCommandService")
    private final AdminCodingPostCommandService service;

    @Operation(
            summary = "코딩풀이 게시물 수정 (관리자)",
            description = "관리자가 코딩풀이 게시물을 수정합니다."
    )
    @PutMapping("/posts/{postId}")
    public ResponseEntity<CodingPost> updatePost(@PathVariable int postId,
                                                 @RequestBody CodingPostRequestDTO dto,
                                                 @LoginMember int memberId) {
        return ResponseEntity.ok(service.updatePost(memberId, postId, dto));
    }

    @Operation(
            summary = "코딩풀이 게시물 삭제 (관리자)",
            description = "관리자가 코딩풀이 게시물을 삭제합니다."
    )
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId,
                                           @LoginMember int memberId) {
        service.deletePost(memberId, postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "코딩풀이 게시물 댓글 수정 (관리자)",
            description = "관리자가 코딩풀이 게시물의 댓글을 수정합니다."
    )
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CodingComment> updateComment(@PathVariable int commentId,
                                                       @RequestBody CodingCommentRequestDTO dto,
                                                       @LoginMember int memberId) {
        return ResponseEntity.ok(service.updateComment(memberId, commentId, dto));
    }

    @Operation(
            summary = "코딩풀이 게시물 댓글 삭제 (관리자)",
            description = "관리자가 코딩풀이 게시물의 댓글을 삭제합니다."
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId,
                                              @LoginMember int memberId) {
        service.deleteComment(memberId, commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "코딩문제 등록 (관리자)",
            description = "관리자가 코딩문제를 등록합니다."
    )
    @PostMapping("/coding-problem/posts")
    public ResponseEntity<CodingProblem> createProblem(@RequestBody CodingProblemRequestDTO dto,
                                                       @LoginMember int memberId) {
        CodingProblem problem = service.createProblem(memberId, dto);
        return ResponseEntity.ok(problem);
    }

    @Operation(
            summary = "코딩문제 수정 (관리자)",
            description = "관리자가 코딩문제를 수정합니다."
    )
    @PutMapping("/coding-problem/{problemId}")
    public ResponseEntity<CodingProblem> updateProblem(@PathVariable int problemId,
                                                       @RequestBody CodingProblemRequestDTO dto,
                                                       @LoginMember int memberId) {
        return ResponseEntity.ok(service.updateProblem(memberId, problemId, dto));
    }

    @Operation(
            summary = "코딩문제 삭제 (관리자)",
            description = "관리자가 코딩문제를 삭제합니다."
    )
    @DeleteMapping("/coding-problem/{problemId}")
    public ResponseEntity<Void> deleteProblem(@PathVariable int problemId,
                                              @LoginMember int memberId) {
        service.deleteProblem(memberId, problemId);
        return ResponseEntity.noContent().build();
    }
}
