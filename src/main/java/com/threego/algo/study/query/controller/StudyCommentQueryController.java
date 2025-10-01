package com.threego.algo.study.query.controller;

import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.service.StudyMemberQueryService;
import com.threego.algo.study.query.service.StudyPostQueryService;
import com.threego.algo.study.query.service.StudyRoadmapQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Study Comment - Member Query",
        description = "회원용 스터디 댓글 API (Query)"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyCommentQueryController {

    private final StudyPostQueryService studyPostQueryService;

    @Operation(
            summary = "스터디 게시물 댓글 조회",
            description = "스터디 게시물의 댓글들을 조회합니다."
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<StudyCommentDTO>> findPostComments(@PathVariable int postId) {
        List<StudyCommentDTO> comments = studyPostQueryService.findStudyPostComments(postId);
        return ResponseEntity.ok(comments);
    }
}
