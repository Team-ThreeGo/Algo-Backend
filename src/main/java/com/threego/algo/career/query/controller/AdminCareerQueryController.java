package com.threego.algo.career.query.controller;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.career.query.dto.PostDetailResponseDto;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;
import com.threego.algo.career.query.service.CareerQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Career Info (Admin)", description = "관리자용 기업별 정보 공유 API")
@RestController
@RequestMapping("/admin/career-info")
public class AdminCareerQueryController {
    private final CareerQueryService careerInfoService;

    @Autowired
    public AdminCareerQueryController(CareerQueryService careerQueryService) {
        this.careerInfoService = careerQueryService;
    }

    @Operation(
            summary = "관리자용 기업별 정보 공유 게시물 목록 조회",
            description = "관리자는 전체 게시물을 조회할 수 있으며, status, keyword 조건을 추가할 수 있습니다."
    )
    @GetMapping("/posts")
    public ResponseEntity<List<PostSummaryResponseDto>> findPostList(
            @RequestParam(required = false) String visibility,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(careerInfoService.findPostList(visibility, status, keyword));
    }

    @Operation(
            summary = "관리자용 기업별 정보 공유 게시물 상세 조회",
            description = "관리자는 모든 게시물을 상세 조회할 수 있습니다."
    )
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailResponseDto> findPost(
            @PathVariable int postId
    ){
        return ResponseEntity.ok(careerInfoService.findPostForAdmin(postId));
    }

    @Operation(
            summary = "관리자용 기업별 정보 공유 게시물의 댓글 전체 조회",
            description = "관리자는 모든 댓글을 조회할 수 있습니다."
    )
    @GetMapping("/comments")
    public ResponseEntity<List<PostDetailResponseDto>> findComments(){
        return ResponseEntity.ok(careerInfoService.findCommentsForAdmin());
    }
}
