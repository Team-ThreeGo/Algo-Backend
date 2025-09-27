package com.threego.algo.coding.query.controller;

import com.threego.algo.coding.query.dto.CodingProblemDetailDTO;
import com.threego.algo.coding.query.dto.CodingProblemSummaryDTO;
import com.threego.algo.coding.query.service.CodingProblemQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CodingProblem", description = "회원용 코딩문제 API")
@RequestMapping("/codingproblem")
@RestController
@AllArgsConstructor
public class CodingProblemQueryController {

    private final CodingProblemQueryService codingProblemQueryService;

    // 회원용 알고리즘 문제 게시물 목록 조회
    // GET /codingproblem/posts
    @GetMapping("/posts")
    public List<CodingProblemSummaryDTO> findProblemsList(
            @RequestParam(value = "keyword", required = false) String keyword) {
        return codingProblemQueryService.findProblemsList(keyword);
    }

    // 회원용 난이도/플랫폼 기준 정렬된 문제 목록 조회
    // GET /codingproblem/posts/difficulty
    @GetMapping("/posts/difficulty")
    public List<CodingProblemSummaryDTO> findProblemsByDifficulty() {
        return codingProblemQueryService.findProblemsByDifficulty();
    }

    // 회원용 알고리즘 게시물 상세 조회
    // GET /codingproblem/posts/{postId}
    @GetMapping("/posts/{postId}")
    public CodingProblemDetailDTO findProblemDetail(@PathVariable int postId) {
        return codingProblemQueryService.findProblemDetail(postId);
    }
}
