package com.threego.algo.coding.query.controller;

import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import com.threego.algo.coding.query.dto.CodingProblemDetailDTO;
import com.threego.algo.coding.query.dto.CodingProblemSummaryDTO;
import com.threego.algo.coding.query.service.CodingProblemQueryService;
import com.threego.algo.coding.query.service.CodingPostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Coding Problem - Member Query",
        description = "회원용 코딩 문제 API (Query)"
)
@RequestMapping("/coding-problem")
@RestController
@AllArgsConstructor
public class CodingProblemQueryController {

    private final CodingProblemQueryService codingProblemQueryService;

    // 회원용 알고리즘 문제 게시물 목록 조회
    // GET /coding-problem/posts
    @Operation(
            summary = "회원용 알고리즘 문제 게시물 목록 조회",
            description = "회원이 알고리즘 문제 게시물 목록을 조회합니다."
    )
    @GetMapping("/posts")
    public List<CodingProblemSummaryDTO> findProblemsList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return codingProblemQueryService.findProblemsList(keyword, page, size);
    }

    // 회원용 난이도/플랫폼 기준 정렬된 문제 목록 조회
    // GET /coding-problem/posts/difficulty
    @Operation(
            summary = "회원용 난이도/플랫폼 기준 정렬된 문제 목록 조회",
            description = "회원이 난이도/플랫폼 기준 정렬된 문제 목록 조회합니다."
    )
    @GetMapping("/posts/difficulty")
    public List<CodingProblemSummaryDTO> findProblemsByDifficulty() {
        return codingProblemQueryService.findProblemsByDifficulty();
    }

    // 회원용 특정 코딩문제에 관련된 게시물 목록 조회
    @Operation(
            summary = "회원용 특정 코딩문제에 관련된 게시물 목록 조회",
            description = "회원이 특정 코딩문제에 관련된 게시물 목록 조회합니다."
    )
    @GetMapping("/{problemId}/posts")
    public List<CodingPostSummaryDTO> findPostsByProblemId(
            @PathVariable int problemId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return codingProblemQueryService.findPostListByProblemId(problemId, keyword, page, size);
    }

    // 회원용 알고리즘 게시물 상세 조회
    // GET /coding-problem/posts/{postId}
    @Operation(
            summary = "회원용 알고리즘 문제 게시물 상세 조회",
            description = "회원이 알고리즘 문제 게시물 상세 조회합니다."
    )
    @GetMapping("/posts/{postId}")
    public CodingProblemDetailDTO findProblemDetail(@PathVariable int postId) {
        return codingProblemQueryService.findProblemDetail(postId);
    }
}
