package com.threego.algo.studyrecruit.query.controller;

import com.threego.algo.studyrecruit.query.dto.*;
import com.threego.algo.studyrecruit.query.service.StudyRecruitPostQueryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Study Recruit Comment - Member Query",
        description = "회원용 스터디 모집 댓글 API (Query)"
)
@RestController
@AllArgsConstructor
@RequestMapping("/study-recruit")
public class StudyRecruitCommentQueryController {

    private final StudyRecruitPostQueryServiceImpl studyRecruitPostServiceImpl;

    @Operation(
            summary = "스터디 모집글 댓글 조회",
            description = "특정 게시글의 댓글을 조회합니다. 부모 댓글과 대댓글이 정렬되어 반환됩니다."
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<StudyRecruitCommentDTO>> findPostComments(@PathVariable int postId) {
        return ResponseEntity.ok(studyRecruitPostServiceImpl.findStudyRecruitComments(postId));
    }
}
