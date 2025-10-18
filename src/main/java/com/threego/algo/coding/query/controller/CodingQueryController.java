package com.threego.algo.coding.query.controller;

import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostListResponseDTO;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
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
            description = "회원이 코딩풀이 게시물 목록 조회 (최신순, 페이징, 검색)합니다. 전체 게시물 수를 함께 반환합니다."
    )
    @GetMapping("/posts")
    public CodingPostListResponseDTO findPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .page(page)
                .size(size)
                .visibility("Y")
                .keyword(keyword)
                .build();

        return codingPostQueryService.findPostListWithPagination(condition);
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