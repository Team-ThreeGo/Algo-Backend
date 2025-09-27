package com.threego.algo.career.query.controller;

import com.threego.algo.career.query.dto.CommentResponseDto;
import com.threego.algo.career.query.dto.PostDetailResponseDto;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;
import com.threego.algo.career.query.service.CareerQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Career Info", description = "회원용 기업별 정보 공유 API")
@RestController
@RequestMapping("/career-info")
public class CareerQueryController {

    private final CareerQueryService careerQueryService;

    @Autowired
    public CareerQueryController(CareerQueryService careerQueryService) {
        this.careerQueryService = careerQueryService;
    }

    @Operation(
            summary = "회원용 기업별 정보 공유 게시물 목록 조회",
            description = "회원은 공개된 게시물만 최신순으로 조회합니다."
    )
    @GetMapping("/posts")
    public ResponseEntity<List<PostSummaryResponseDto>> findPostList(
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(careerQueryService.findPostList("Y", null, keyword));
    }

    @Operation(
            summary = "회원용 기업별 정보 공유 게시물 상세 조회",
            description = "회원은 공개된 게시물만 상세 조회할 수 있습니다."
    )
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailResponseDto> findPost(
            @PathVariable int postId
    ){
        return ResponseEntity.ok(careerQueryService.findPostForMember(postId));
    }

    @Operation(
            summary = "기업별 정보 공유 게시물의 댓글 조회",
            description = "특정 게시물에 달린 모든 댓글을 조회합니다. " +
                    "visibility 여부와 관계없이 모두 내려주며, 프론트에서 처리(블러)합니다."
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findComments(
            @PathVariable int postId
    ){
        return ResponseEntity.ok(careerQueryService.findCommentsByPostId(postId));
    }
}
