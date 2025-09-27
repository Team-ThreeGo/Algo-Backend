package com.threego.algo.studyrecruit.query.controller;

import com.threego.algo.studyrecruit.query.dto.StudyRecruitCommentDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitDetailDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitPostDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitSearchDTO;
import com.threego.algo.studyrecruit.query.service.StudyRecruitPostQueryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[Admin] Study Recruit API", description = "관리자용 스터디 모집글 조회 API")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/study-recruit")
public class AdminStudyRecruitPostQueryController {

    private final StudyRecruitPostQueryServiceImpl studyRecruitPostServiceImpl;

    @Operation(
            summary = "관리자용 숨김 처리된 스터디 모집글 목록 조회",
            description = "관리자는 숨김 처리된 게시글의 목록을 조회할 수 있습니다."
    )
    @GetMapping("/posts/hidden")
    public ResponseEntity<List<StudyRecruitPostDTO>> findHiddenPosts(
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

        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitListIncludeHidden(searchDto));
    }

    @Operation(
            summary = "관리자용 숨김 처리된 스터디 모집글 상세 조회",
            description = "관리자는 숨김 처리된 게시글을 상세 조회할 수 있습니다."
    )
    @GetMapping("/posts/{postId}/hidden")
    public ResponseEntity<StudyRecruitDetailDTO> findHiddenPost(@PathVariable Long postId) {
        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitDetailIncludeHidden(postId));
    }

    @Operation(
            summary = "관리자용 숨김 댓글 목록 조회",
            description = "관리자는 모든 스터디 모집글의 숨김 처리 된 댓글의 목록을 조회할 수 있습니다."
    )
    @GetMapping("/comments/hidden")
    public ResponseEntity<List<StudyRecruitCommentDTO>> findAllHiddenComments(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        StudyRecruitSearchDTO searchDto = StudyRecruitSearchDTO.builder()
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(studyRecruitPostServiceImpl.findAllStudyRecruitCommentsIncludeHidden(searchDto));
    }

    @Operation(
            summary = "관리자용 숨김 처리된 스터디 모집글의 댓글 조회",
            description = "관리자는 숨김 처리된 게시글의 댓글도 조회할 수 있습니다."
    )
    @GetMapping("/posts/{postId}/comments/hidden")
    public ResponseEntity<List<StudyRecruitCommentDTO>> findHiddenPostComments(@PathVariable Long postId) {
        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitCommentsIncludeHidden(postId));
    }
}