package com.threego.algo.study.query.controller;

import com.threego.algo.study.query.dto.*;
import com.threego.algo.study.query.service.StudyMemberQueryService;
import com.threego.algo.study.query.service.StudyPostQueryService;
import com.threego.algo.study.query.service.StudyRoadmapQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Study API", description = "스터디 관련 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyPostQueryController {

    private final StudyPostQueryService studyPostQueryService;
    private final StudyMemberQueryService studyMemberQueryService;
    private final StudyRoadmapQueryService studyRoadmapQueryService;

    @Operation(
            summary = "스터디 멤버 목록 조회",
            description = "스터디 그룹의 전체 멤버 목록을 조회합니다."
    )
    @GetMapping("/{studyId}/members")
    public ResponseEntity<List<StudyMemberDTO>> findMembers(@PathVariable Integer studyId) {
        List<StudyMemberDTO> members = studyMemberQueryService.findAllStudyMember(studyId);
        return ResponseEntity.ok(members);
    }

    @Operation(
            summary = "스터디 게시물 목록 조회",
            description = "스터디 그룹의 게시물 목록을 페이징으로 조회합니다."
    )
    @GetMapping("/{studyId}/posts")
    public ResponseEntity<List<StudyPostDTO>> findPosts(
            @PathVariable Integer studyId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .studyId(studyId)
                .page(page)
                .size(size)
                .build();

        List<StudyPostDTO> posts = studyPostQueryService.findAllStudyPosts(searchDto);
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "스터디 게시물 상세 조회",
            description = "스터디 게시물의 상세 정보와 이미지를 조회합니다."
    )
    @GetMapping("/posts/{postId}")
    public ResponseEntity<StudyPostDetailDTO> findPostDetail(@PathVariable Integer postId) {
        StudyPostDetailDTO postDetail = studyPostQueryService.findStudyPostDetail(postId);
        return ResponseEntity.ok(postDetail);
    }

    @Operation(
            summary = "스터디 게시물 댓글 조회",
            description = "스터디 게시물의 댓글들을 조회합니다."
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<StudyCommentDTO>> findPostComments(@PathVariable Integer postId) {
        List<StudyCommentDTO> comments = studyPostQueryService.findStudyPostComments(postId);
        return ResponseEntity.ok(comments);
    }

    @Operation(
            summary = "스터디 로드맵 목록 조회",
            description = "스터디 그룹의 전체 로드맵 목록을 조회합니다."
    )
    @GetMapping("/{studyId}/roadmaps")
    public ResponseEntity<List<StudyRoadmapDTO>> findRoadmaps(@PathVariable Integer studyId) {
        List<StudyRoadmapDTO> roadmaps = studyRoadmapQueryService.findAllStudyRoadmap(studyId);
        return ResponseEntity.ok(roadmaps);
    }

    @Operation(
            summary = "스터디 로드맵 상세 조회",
            description = "스터디 로드맵의 상세 정보와 마일스톤 목록을 조회합니다."
    )
    @GetMapping("/roadmaps/{roadmapId}")
    public ResponseEntity<StudyRoadmapDetailDTO> findRoadmapDetail(@PathVariable Integer roadmapId) {
        StudyRoadmapDetailDTO roadmapDetail = studyRoadmapQueryService.findStudyRoadmapDetail(roadmapId);
        return ResponseEntity.ok(roadmapDetail);
    }

}
