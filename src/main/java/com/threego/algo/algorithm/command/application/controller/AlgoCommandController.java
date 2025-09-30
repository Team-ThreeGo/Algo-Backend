package com.threego.algo.algorithm.command.application.controller;

import com.threego.algo.algorithm.command.application.dto.AlgoCommentRequestDTO;
import com.threego.algo.algorithm.command.application.service.AlgoCommandService;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import com.threego.algo.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Algorithm - Member Command",
        description = "회원용 알고리즘 학습 API (Command)"
)
@RequestMapping("/algo")
@RequiredArgsConstructor
@RestController
public class AlgoCommandController {
    private final AlgoCommandService algoCommandService;
    private final JwtUtil jwtUtil;
    private final MemberCommandRepository memberCommandRepository;

    @Operation(summary = "알고리즘 학습 게시물 댓글 등록", description = "회원이 알고리즘 학습 게시물에 댓글을 등록하는 API입니다.")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable("postId") final int postId,
                                              @RequestBody final AlgoCommentRequestDTO request) throws Exception {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        algoCommandService.createComment(1, postId, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알고리즘 학습 게시물 댓글 수정", description = "회원이 알고리즘 학습 게시물에 댓글을 수정하는 API입니다.")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable("commentId") final int commentId,
                                              @RequestBody final AlgoCommentRequestDTO request) throws Exception {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        algoCommandService.updateComment(1, commentId, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알고리즘 학습 게시물 댓글 삭제", description = "회원이 알고리즘 학습 게시물에 댓글을 삭제하는 API입니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") final int commentId) throws Exception {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        algoCommandService.deleteComment(1, commentId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "퀴즈 정답 제출",
            description = "회원이 알고리즘 학습 게시물의 퀴즈에 답을 제출하고, 정답일 경우 맞힌 기록을 저장하는 API입니다.")
    @PostMapping("/quizzes/{questionId}/answers")
    public ResponseEntity<Void> createCorrectQuizHistory(@PathVariable("questionId") final int questionId,
                                                         HttpServletRequest request) throws Exception {

        // TODO. gateway filter 적용
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);

        String email = claims.getSubject(); // sub
        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int memberId = member.getId();

//        algoCommandService.createCorrectQuizHistory(1, questionId);

        algoCommandService.createCorrectQuizHistory(memberId, questionId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알고리즘 학습 게시물 추천",
            description = "회원이 알고리즘 학습 게시물을 추천하는 API입니다.")
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<Void> createAlgoPostLikes(@PathVariable("postId") final int postId) {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        algoCommandService.createAlgoPostLikes(1, postId);

        return ResponseEntity.ok().build();
    }
}