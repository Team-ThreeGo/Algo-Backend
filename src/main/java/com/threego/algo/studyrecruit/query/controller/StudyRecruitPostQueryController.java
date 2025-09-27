package com.threego.algo.studyrecruit.query.controller;

import com.threego.algo.studyrecruit.query.dto.*;
import com.threego.algo.studyrecruit.query.service.StudyRecruitPostQueryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Study Recruit API", description = "스터디 모집글 조회 API")
@RestController
@AllArgsConstructor
@RequestMapping("/study-recruit")
public class StudyRecruitPostQueryController {

    private final StudyRecruitPostQueryServiceImpl studyRecruitPostServiceImpl;

    @Operation(
            summary = "스터디 모집글 목록 조회",
            description = "전체 스터디 모집글 리스트를 조회합니다.(페이징, 검색)"
    )
    @GetMapping("/posts")
    public ResponseEntity<List<StudyRecruitPostDTO>> findPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword
    ) {
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .page(page)
                .size(size)
                .status(status)
                .keyword(keyword)
                .build();

        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitList(searchDto));
    }

    @Operation(
            summary = "스터디 모집글 상세 조회",
            description = "POST_ID로 특정 스터디 모집글의 상세 정보를 조회합니다."
    )
    @GetMapping("/posts/{postId}")
    public ResponseEntity<StudyRecruitDetailDTO> findPost(@PathVariable Long postId) {
        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitDetail(postId));
    }

    @Operation(
            summary = "스터디 모집글 댓글 조회",
            description = "특정 게시글의 댓글을 조회합니다. 부모 댓글과 대댓글이 정렬되어 반환됩니다."
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<StudyRecruitCommentDTO>> findPostComments(@PathVariable Long postId) {
        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitComments(postId));
    }

    @Operation(
            summary = "스터디 모집글 참가 신청자 목록 조회",
            description = "특정 게시글의 참가 신청자 목록을 조회합니다. 작성자만 조회 가능합니다."
    )
    @GetMapping("/posts/{postId}/members")
    public ResponseEntity<List<StudyRecruitMemberDTO>> findPostMembers(@PathVariable Long postId) {
        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitMembers(postId));
    }


}