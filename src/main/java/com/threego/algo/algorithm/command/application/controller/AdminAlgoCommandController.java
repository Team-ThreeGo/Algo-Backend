package com.threego.algo.algorithm.command.application.controller;

import com.threego.algo.algorithm.command.application.dto.*;
import com.threego.algo.algorithm.command.application.service.AlgoCommandService;
import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(
        name = "Algorithm - Admin Command",
        description = "관리자용 알고리즘 학습 API (Command)"
)
@RequestMapping("/admin/algo")
@RequiredArgsConstructor
@RestController
public class AdminAlgoCommandController {
    private final AlgoCommandService algoCommandService;

    @Operation(summary = "알고리즘 학습 로드맵 등록", description = "관리자가 알고리즘 학습 로드맵을 등록하는 API입니다.")
    @PostMapping("/roadmaps")
    public ResponseEntity<AlgoRoadmapResponseDTO> createAlgoRoadmap(@RequestBody final AlgoRoadmapRequestDTO request) {
        final AlgoRoadmap algoRoadmap = algoCommandService.createAlgoRoadmap(request);

        final AlgoRoadmapResponseDTO response = AlgoRoadmapResponseDTO.of(algoRoadmap);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 로드맵 수정", description = "관리자가 알고리즘 학습 로드맵을 수정하는 API입니다.")
    @PutMapping("/roadmaps/{roadmapId}")
    public ResponseEntity<AlgoRoadmapResponseDTO> updateAlgoRoadmap(@PathVariable("roadmapId") final int roadmapId,
                                                                    @RequestBody final AlgoRoadmapRequestDTO request) throws Exception {
        final AlgoRoadmap algoRoadmap = algoCommandService.updateAlgoRoadmap(roadmapId, request);

        final AlgoRoadmapResponseDTO response = AlgoRoadmapResponseDTO.of(algoRoadmap);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "알고리즘 학습 게시물 등록", description = "관리자가 알고리즘 학습 게시물을 등록하는 API입니다.")
    @PostMapping(value = "/roadmaps/{roadmapId}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlgoPostDetailResponseDTO> createAlgoPost(
            @PathVariable("roadmapId") int roadmapId,
            @Parameter(description = "제목") @RequestParam String title,
            @Parameter(description = "내용") @RequestParam String content,
            @Parameter(description = "이미지 파일들 (최대 5MB, JPG/PNG/GIF 등)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws Exception {

        AlgoPostRequestDTO request = new AlgoPostRequestDTO();
        request.setTitle(title);
        request.setContent(content);
        request.setImages(images);

        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        final AlgoPostDetailResponseDTO response = algoCommandService.createAlgoPost(29, roadmapId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 게시물 삭제", description = "관리자가 알고리즘 학습 게시물을 삭제하는 API입니다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deleteAlgoPost(@PathVariable("postId") final int postId) throws Exception {
        algoCommandService.deleteAlgoPost(postId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "알고리즘 학습 게시물 퀴즈 등록", description = "관리자가 알고리즘 학습 게시물 퀴즈를 등록하는 API입니다.")
    @PostMapping("/posts/{postId}/quizzes")
    public ResponseEntity<AlgoQuizQuestionResponseDTO> createAlgoQuiz(@PathVariable("postId") final int postId,
                                                                      @RequestBody final AlgoQuizQuestionRequestDTO request) throws Exception {
        AlgoQuizQuestionResponseDTO response = algoCommandService.createAlgoQuiz(postId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 게시물 퀴즈 수정", description = "관리자가 알고리즘 학습 게시물 퀴즈를 수정하는 API입니다." +
            "퀴즈 질문 내용과 보기 내용만 수정 가능합니다.")
    @PutMapping("/quizzes/{quizQuestionId}")
    public ResponseEntity<AlgoQuizQuestionResponseDTO> updateAlgoQuiz(@PathVariable("quizQuestionId") final int quizQuestionId,
                                                                      @RequestBody final UpdateAlgoQuizQuestionRequestDTO request) throws Exception {
        AlgoQuizQuestionResponseDTO response = algoCommandService.updateAlgoQuiz(quizQuestionId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 게시물 수정", description = "관리자가 알고리즘 학습 게시물을 수정하는 API입니다." +
            "퀴즈 질문 내용과 보기 내용만 수정 가능합니다.")
    @PutMapping("/algo/posts/{postId}")
    public ResponseEntity<AlgoPostDetailResponseDTO> updateAlgoPost(@PathVariable("postId") final int postId,
                                                                    @RequestBody final AlgoPostRequestDTO request) throws Exception {
        AlgoPostDetailResponseDTO response = algoCommandService.updateAlgoPost(postId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알고리즘 학습 게시물 댓글 삭제", description = "관리자가 알고리즘 학습 게시물 댓글을 삭제하는 API입니다.")
    @DeleteMapping("/algo/comments/{commentId}")
    public ResponseEntity<Void> deleteAlgoComment(@PathVariable("commentId") final int commentId) throws Exception {
        algoCommandService.deleteCommentForAdmin(commentId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알고리즘 학습 게시물 퀴즈 삭제", description = "관리자가 알고리즘 학습 게시물 댓글을 삭제하는 API입니다.")
    @DeleteMapping("/quizzes/{quizQuestionId}")
    public ResponseEntity<Void> deleteAlgoQuizQuestion(@PathVariable("quizQuestionId") final int quizQuestionId) throws Exception {
        algoCommandService.deleteAlgoQuizQuestion(quizQuestionId);

        return ResponseEntity.ok().build();
    }
}