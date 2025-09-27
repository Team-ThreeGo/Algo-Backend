package com.threego.algo.study.query.controller;

import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;
import com.threego.algo.study.query.service.StudyPostQueryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "[Admin] Study API", description = "관리자용 스터디 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/study")
public class AdminStudyPostQueryController {

    private final StudyPostQueryServiceImpl studyPostQueryServiceImpl;

    @Operation(
            summary = "숨김 처리된 스터디 게시물 목록 조회",
            description = "관리자가 숨김 처리된 스터디 게시물 목록을 조회합니다."
    )
    @GetMapping("/posts/hidden")
    public ResponseEntity<List<StudyPostDTO>> findHiddenPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .page(page)
                .size(size)
                .build();

        List<StudyPostDTO> posts = studyPostQueryServiceImpl.findAllHiddenStudyPosts(searchDto);
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "숨김 처리된 스터디 게시물 상세 조회",
            description = "관리자가 숨김 처리된 스터디 게시물을 상세 조회합니다."
    )
    @GetMapping("/posts/{postId}/hidden")
    public ResponseEntity<StudyPostDetailDTO> findHiddenPostDetail(@PathVariable Integer postId) {
        StudyPostDetailDTO postDetail = studyPostQueryServiceImpl.findHiddenStudyPostDetail(postId);
        return ResponseEntity.ok(postDetail);
    }

    @Operation(
            summary = "숨김 처리된 스터디 게시물의 댓글 목록 조회",
            description = "관리자가 숨김 처리된 스터디 게시물의 댓글 목록을 조회합니다."
    )
    @GetMapping("/comments/hidden")
    public ResponseEntity<List<StudyCommentDTO>> findHiddenComments(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        StudyPostSearchDTO searchDto = StudyPostSearchDTO.builder()
                .page(page)
                .size(size)
                .build();

        List<StudyCommentDTO> comments = studyPostQueryServiceImpl.findAllHiddenStudyComments(searchDto);
        return ResponseEntity.ok(comments);
    }
}
