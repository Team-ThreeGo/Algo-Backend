package com.threego.algo.study.query.controller;

import com.threego.algo.study.query.dto.*;
import com.threego.algo.study.query.service.StudyPostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Study Post - Member Query",
        description = "회원용 스터디 게시물 API (Query)"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyPostQueryController {

    private final StudyPostQueryService studyPostQueryService;

    @Operation(
            summary = "스터디 게시물 목록 조회",
            description = "스터디 그룹의 게시물 목록을 페이징으로 조회합니다. 전체 게시물 수를 함께 반환합니다."
    )
    @GetMapping("/{studyId}/posts")
    public ResponseEntity<StudyPostListResponseDTO> findPosts(
            @PathVariable int studyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(studyId)
                .page(page)
                .size(size)
                .build();

        StudyPostListResponseDTO response = studyPostQueryService.findAllStudyPosts(searchDto);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "스터디 게시물 상세 조회",
            description = "스터디 게시물의 상세 정보와 이미지를 조회합니다."
    )
    @GetMapping("/posts/{postId}")
    public ResponseEntity<StudyPostDetailDTO> findPostDetail(@PathVariable int postId) {
        StudyPostDetailDTO postDetail = studyPostQueryService.findStudyPostDetail(postId);
        return ResponseEntity.ok(postDetail);
    }



}
