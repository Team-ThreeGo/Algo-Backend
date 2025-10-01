package com.threego.algo.studyrecruit.command.application.controller;


import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import com.threego.algo.security.JwtUtil;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitMemberService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study-recruit/applicants")
@RequiredArgsConstructor
@Tag(
        name = "Study Recruit Comment - Member Command",
        description = "회원용 스터디 모집 댓글 API (Command)"
)
public class StudyRecruitMemberCommandController {

    private final StudyRecruitMemberService studyRecruitMemberService;
    private final JwtUtil jwtUtil;
    private final MemberCommandRepository memberCommandRepository;

    @Operation(summary = "신청 취소", description = "신청자가 자신의 신청을 취소합니다.")
    @DeleteMapping("/{joinId}")
    public ResponseEntity<String> cancelApplication(
            @PathVariable int joinId,
            @RequestHeader("Member-Id") int memberId) {
        return studyRecruitMemberService.cancelApplication(joinId, memberId);
    }

    @Operation(summary = "신청 승인", description = "모집글 작성자가 신청을 승인합니다.")
    @PostMapping("/{joinId}/accept")
    public ResponseEntity<String> acceptApplication(
            @PathVariable int joinId,
            @RequestHeader("Member-Id") int authorId) {
        return studyRecruitMemberService.acceptApplication(joinId, authorId);
    }

    @Operation(summary = "신청 거절", description = "모집글 작성자가 신청을 거절합니다.")
    @PostMapping("/{joinId}/reject")
    public ResponseEntity<String> rejectApplication(
            HttpServletRequest request,
            @PathVariable int joinId) {

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String email = claims.getSubject();

        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int authorId = member.getId();

        return studyRecruitMemberService.rejectApplication(joinId, authorId);
    }
}
