package com.threego.algo.studyrecruit.command.application.controller;

import com.threego.algo.common.auth.LoginMember;
import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitPostCreateDTO;
import com.threego.algo.studyrecruit.command.application.dto.update.StudyRecruitPostUpdateDTO;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitMemberService;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study-recruit/posts")
@RequiredArgsConstructor
@Tag(
        name = "Study Recruit - Member Command",
        description = "회원용 스터디 모집글 API (Command)"
)
public class StudyRecruitPostCommandController {
    private final StudyRecruitPostService studyRecruitPostService;
    private final StudyRecruitMemberService studyRecruitMemberService;


    @Operation(summary = "모집글 등록", description = "새로운 스터디 모집글을 등록합니다.")
    @PostMapping
    public ResponseEntity<String> createPost(
            @LoginMember int memberId,
            @Valid @RequestBody StudyRecruitPostCreateDTO request) {
        studyRecruitPostService.createPost(memberId, request);
        return ResponseEntity.status(201).body("모집글이 성공적으로 등록되었습니다.");
    }

    @Operation(summary = "모집글 수정", description = "작성자가 자신의 모집글을 수정합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable int postId,
            @LoginMember int memberId,
            @Valid @RequestBody StudyRecruitPostUpdateDTO request) {
        studyRecruitPostService.updatePost(postId, memberId, request);
        return ResponseEntity.ok("모집글이 성공적으로 수정되었습니다.");
    }

    @Operation(summary = "모집글 삭제", description = "작성자가 자신의 모집글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable int postId,
            @LoginMember int memberId) {
        studyRecruitPostService.deletePost(postId, memberId);
        return ResponseEntity.ok("모집글이 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "모집 마감", description = "작성자가 모집을 마감합니다.")
    @PostMapping("/{postId}/close")
    public ResponseEntity<String> closeRecruitment(
            @PathVariable int postId,
            @LoginMember int memberId) {
        studyRecruitPostService.closeRecruitment(postId, memberId);
        return ResponseEntity.ok("모집이 성공적으로 마감되었습니다.");
    }



    @Operation(summary = "참가 신청", description = "스터디에 참가 신청을 합니다.")
    @PostMapping("/{postId}/applicants")
    public ResponseEntity<String> applyToStudy(
            @PathVariable int postId,
            @LoginMember int memberId) {
        return studyRecruitMemberService.applyToStudy(postId, memberId);
    }

}
