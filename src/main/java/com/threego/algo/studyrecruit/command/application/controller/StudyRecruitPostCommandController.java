package com.threego.algo.studyrecruit.command.application.controller;

import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import com.threego.algo.security.JwtUtil;
import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitPostCreateDTO;
import com.threego.algo.studyrecruit.command.application.dto.update.StudyRecruitPostUpdateDTO;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitMemberService;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitPostService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;
    private final MemberCommandRepository memberCommandRepository;

    @Operation(summary = "모집글 등록", description = "새로운 스터디 모집글을 등록합니다.")
    @PostMapping
    public ResponseEntity<String> createPost(
            HttpServletRequest request,
            @Valid @RequestBody StudyRecruitPostCreateDTO dto) {

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String email = claims.getSubject();

        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int memberId = member.getId();

        return studyRecruitPostService.createPost(memberId, dto);
    }

    @Operation(summary = "모집글 수정", description = "작성자가 자신의 모집글을 수정합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable int postId,
            @RequestHeader("Member-Id") int memberId,
            @Valid @RequestBody StudyRecruitPostUpdateDTO request) {
        return studyRecruitPostService.updatePost(postId, memberId, request);
    }

    @Operation(summary = "모집글 삭제", description = "작성자가 자신의 모집글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable int postId,
            @RequestHeader("Member-Id") int memberId) {
        return studyRecruitPostService.deletePost(postId, memberId);
    }

    @Operation(summary = "모집 마감", description = "작성자가 모집을 마감합니다.")
    @PostMapping("/{postId}/close")
    public ResponseEntity<String> closeRecruitment(
            @PathVariable int postId,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String email = claims.getSubject();

        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int memberId = member.getId();

        return studyRecruitPostService.closeRecruitment(postId, memberId);
    }



    @Operation(summary = "참가 신청", description = "스터디에 참가 신청을 합니다.")
    @PostMapping("/{postId}/applicants")
    public ResponseEntity<String> applyToStudy(
            @PathVariable int postId,
            HttpServletRequest request) throws Exception {

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String email = claims.getSubject();

        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int memberId = member.getId();

        return studyRecruitMemberService.applyToStudy(postId, memberId);
    }

}
