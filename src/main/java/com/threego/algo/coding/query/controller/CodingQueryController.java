package com.threego.algo.coding.query.controller;

import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import com.threego.algo.coding.query.service.CodingPostQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coding", description = "회원용 코딩풀이 게시물 API")
@RequestMapping("/coding")
@RestController
@AllArgsConstructor
public class CodingQueryController {

    private final CodingPostQueryService codingPostQueryService;

    /**
     * 회원용 코딩풀이 게시물 목록 조회 (최신순)
     */
    @GetMapping("/posts")
    public List<CodingPostSummaryDTO> findPostList(
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        return codingPostQueryService.findPostList(keyword);
    }

    /**
     * 회원용 코딩풀이 게시물 상세 조회
     * GET /coding/posts/{postId}
     */
    @GetMapping("/posts/{postId}")
    public CodingPostDetailDTO findPostDetail(@PathVariable int postId) {
        return codingPostQueryService.findPostDetail(postId);
    }

    /**
     * 회원용 코딩풀이 댓글 목록 조회
     * GET /coding/posts/{postId}/comments
     */
    @GetMapping("/posts/{postId}/comments")
    public List<CodingPostCommentDTO> findCommentsByPostId(@PathVariable int postId) {
        return codingPostQueryService.findCommentsByPostId(postId);
    }

}

