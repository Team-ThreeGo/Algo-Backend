package com.threego.algo.study.query.controller;

import com.threego.algo.common.auth.LoginMember;
import com.threego.algo.study.query.dto.*;
import com.threego.algo.study.query.service.StudyMemberQueryService;
import com.threego.algo.study.query.service.StudyPostQueryService;
import com.threego.algo.study.query.service.StudyRoadmapQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            description = "스터디 그룹의 게시물 목록을 페이징으로 조회합니다."
    )
    @GetMapping("/{studyId}/posts")
    public ResponseEntity<List<StudyPostDTO>> findPosts(
            @PathVariable int studyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @LoginMember int memberId) {

        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(studyId)
                .page(page)
                .size(size)
                .build();

        List<StudyPostDTO> posts = studyPostQueryService.findAllStudyPosts(searchDto, memberId);
        return ResponseEntity.ok(posts);
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
