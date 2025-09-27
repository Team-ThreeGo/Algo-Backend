package com.threego.algo.algorithm.query.controller;

import com.threego.algo.algorithm.query.dto.*;
import com.threego.algo.algorithm.query.service.AlgoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Algorithm", description = "회원용 알고리즘 학습 API")
@RequestMapping("/algo")
@RestController
public class AlgoQueryController {
    private final AlgoQueryService algoQueryService;

    @Autowired
    public AlgoQueryController(AlgoQueryService algoQueryService) {
        this.algoQueryService = algoQueryService;
    }

    @Operation(summary = "알고리즘 학습 로드맵 목록 조회", description = "회원이 알고리즘 학습 로드맵 목록을 확인할 수 있는 API입니다.")
    @GetMapping("/roadmaps")
    public ResponseEntity<List<AlgoRoadmapResponseDTO>> findAllAlgoRoadMaps() {
        final List<AlgoRoadmapResponseDTO> response = algoQueryService.findAllAlgoRoadmaps().stream()
                .map(AlgoRoadmapResponseDTO::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특정 알고리즘 학습 로드맵의 게시물 목록 조회",
            description = "회원이 특정 알고리즘 학습 로드맵의 게시물 목록을 조회할 수 있는 API입니다.")
    @GetMapping("/roadmaps/{roadmapId}/posts")
    public ResponseEntity<List<AlgoPostSummaryResponseDTO>> findAlgoPostsByRoadmapId(@PathVariable("roadmapId") final int roadmapId,
                                                                                     @RequestParam(required = false) final String keyword) {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        final List<AlgoPostSummaryResponseDTO> response = algoQueryService.findAlgoPostsByRoadmapId(1, roadmapId, keyword);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 로드맵의 게시물 세부 조회",
            description = "회원이 알고리즘 학습 로드맵의 게시물을 세부 조회할 수 있는 API입니다.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<AlgoPostDetailResponseDTO> findAlgoPostsByPostId(@PathVariable("postId") final int postId) {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        final AlgoPostDetailResponseDTO response = algoQueryService.findAlgoPostByPostId(1, postId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 로드맵의 게시물 댓글 목록 조회",
            description = "회원이 알고리즘 학습 로드맵의 게시물 댓글 목록을 조회할 수 있는 API입니다.")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<AlgoPostCommentDTO>> findCommentsByPostId(@PathVariable("postId") final int postId) {
        final List<AlgoPostCommentDTO> response = algoQueryService.findCommentsByPostId(postId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 로드맵의 게시물 퀴즈(문제 및 보기) 목록 조회",
            description = "회원이 알고리즘 학습 로드맵의 게시물 퀴즈(문제 및 보기)목록을 조회할 수 있는 API입니다.")
    @GetMapping("/posts/{postId}/quizzes")
    public ResponseEntity<List<AlgoQuizResponseDTO>> findQuizQuestionAndOptionByPostId(@PathVariable("postId") final int postId) {
        final List<AlgoQuizResponseDTO> response = algoQueryService.findQuizQuestionAndOptionByPostId(postId);

        return ResponseEntity.ok(response);
    }
}