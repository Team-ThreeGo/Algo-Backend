package com.threego.algo.coding.command.application.controller;

import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingProblemRequestDTO;
import com.threego.algo.coding.command.application.service.AdminCodingPostCommandService;
import com.threego.algo.coding.command.domain.aggregate.*;
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

    /* 설명. 게시글 수정 */
    @PutMapping("/posts/{postId}")
    public ResponseEntity<CodingPost> updatePost(@PathVariable int postId,
                                                 @RequestBody CodingPostRequestDTO dto,
                                                 @RequestParam int adminId) {
        return ResponseEntity.ok(service.updatePost(postId, dto));
    }

    /* 설명. 게시글 삭제 */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId,
                                           @RequestParam int adminId) {
        service.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    /* 설명. 댓글 수정 */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CodingComment> updateComment(@PathVariable int commentId,
                                                       @RequestBody CodingCommentRequestDTO dto,
                                                       @RequestParam int adminId) {
        return ResponseEntity.ok(service.updateComment(commentId, dto));
    }

    /* 설명. 댓글 삭제 */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId,
                                              @RequestParam int adminId) {
        service.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    /* 설명. 코딩문제 등록 */
    @PostMapping("/coding-problem/posts")
    public ResponseEntity<CodingProblem> createProblem(@RequestBody CodingProblemRequestDTO dto,
                                                       @RequestParam int adminId) {
        CodingProblem problem = service.createProblem(dto);
        return ResponseEntity.ok(problem);
    }

    /* 설명. 코딩문제 수정 */
    @PutMapping("/coding-problem/{problemId}")
    public ResponseEntity<CodingProblem> updateProblem(@PathVariable int problemId,
                                                       @RequestBody CodingProblemRequestDTO dto,
                                                       @RequestParam int adminId) {
        return ResponseEntity.ok(service.updateProblem(problemId, dto));
    }

    /* 설명. 코딩문제 삭제 */
    @DeleteMapping("/coding-problem/{problemId}")
    public ResponseEntity<Void> deleteProblem(@PathVariable int problemId,
                                              @RequestParam int adminId) {
        service.deleteProblem(problemId);
        return ResponseEntity.noContent().build();
    }

    /* 설명. 관리자 계정 검증 */
    private void validateAdmin(int adminId) {
        if (adminId != 29 && adminId != 30) {
            throw new IllegalArgumentException("관리자 권한 없음");
        }
    }
}
