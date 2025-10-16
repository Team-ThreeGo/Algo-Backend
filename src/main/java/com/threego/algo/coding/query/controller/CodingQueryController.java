package com.threego.algo.coding.query.controller;

import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import com.threego.algo.coding.query.service.CodingPostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Coding - Member Query",
        description = "회원용 코딩 풀이 게시물 API (Query)"
)
@RequestMapping("/coding")
@RestController
@AllArgsConstructor
public class CodingQueryController {

    private final CodingPostQueryService codingPostQueryService;

    /* 설명. 회원용 코딩풀이 게시물 목록 조회 (최신순) */
    @Operation(
            summary = "회원용 코딩풀이 게시물 목록 조회 (최신순)",
            description = "회원이 코딩풀이 게시물 목록 조회 (최신순)합니다."
    )
    @GetMapping("/posts")
    public List<CodingPostSummaryDTO> findPostList(
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        return codingPostQueryService.findPostList(keyword);
    }

    /* 설명. 회원용 코딩풀이 게시물 상세 조회 */
    @Operation(
            summary = "회원용 코딩풀이 게시물 상세 조회",
            description = "회원이 코딩풀이 게시물 상세 조회합니다."
    )
    @GetMapping("/posts/{postId}")
    public CodingPostDetailDTO findPostDetail(@PathVariable int postId) {
        return codingPostQueryService.findPostDetail(postId);
    }

    /* 설명. 회원용 코딩풀이 댓글 목록 조회 */
    @Operation(
            summary = "회원용 코딩풀이 댓글 목록 조회",
            description = "회원이 코딩풀이 댓글 목록 조회합니다."
    )
    @GetMapping("/posts/{postId}/comments")
    public List<CodingPostCommentDTO> findCommentsByPostId(@PathVariable int postId) {
        return codingPostQueryService.findCommentsByPostId(postId);
    }

}