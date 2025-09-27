package com.threego.algo.coding.query.controller;

import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import com.threego.algo.coding.query.service.CodingPostQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coding (Admin)", description = "관리자용 코딩풀이 게시물 API")
@RequestMapping("/admin/coding")
@RestController
@RequiredArgsConstructor
public class AdminCodingQueryController {

    private final CodingPostQueryService codingPostQueryService;

    /**
     * 관리자용 코딩풀이 게시물 목록 조회
     */
    @GetMapping("/posts")
    public List<CodingPostSummaryDTO> findPostListForAdmin(
            @RequestParam(value = "visibility", required = false) String visibility, // Y/N/ALL
            @RequestParam(value = "keyword", required = false) String keyword ) {
        return codingPostQueryService.findPostListForAdmin(visibility, keyword);
    }

    /**
     * 관리자용 코딩풀이 게시물 상세 조회
     * GET /admin/coding/posts/{postId}
     */
    @GetMapping("/posts/{postId}")
    public CodingPostDetailDTO findPostDetailForAdmin(@PathVariable int postId) {
        return codingPostQueryService.findPostDetailForAdmin(postId);
    }

    /**
     * 관리자용 코딩풀이 댓글 목록 조회
     * GET /admin/coding/comments
     */
    @GetMapping("/comments")
    public List<CodingPostCommentDTO> findCommentsForAdmin() {
        return codingPostQueryService.findCommentsForAdmin();
    }
}
