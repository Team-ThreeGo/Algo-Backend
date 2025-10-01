package com.threego.algo.study.command.application.controller;


import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import com.threego.algo.security.JwtUtil;
import com.threego.algo.study.command.application.dto.create.StudyCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyUpdateDTO;
import com.threego.algo.study.command.application.service.StudyService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(
        name = "Study - Member Command",
        description = "회원용 스터디 그룹 API (Command)"
)
public class StudyCommandController {

    private final StudyService studyService;
    private final JwtUtil jwtUtil;
    private final MemberCommandRepository memberCommandRepository;

    @Operation(
            summary = "스터디 그룹 생성",
            description = "모집 마감된 게시물에서 승인된 멤버들로 스터디 그룹을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "스터디 그룹 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (모집 미마감, 중복 생성 등)"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (작성자가 아님)"),
            @ApiResponse(responseCode = "404", description = "모집글을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping
    public ResponseEntity<String> createStudy(
            HttpServletRequest request,
            @Parameter(description = "작성자 ID", required = true)
            @RequestHeader("Member-Id") int authorId,

            @Parameter(description = "모집글 ID", required = true)
            @RequestHeader("POST-ID") int post_id,

            @Parameter(description = "스터디 생성 정보", required = true)
            @Valid @RequestBody StudyCreateDTO dto) {

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String email = claims.getSubject();

        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int memberId = member.getId();

        return studyService.createStudyFromRecruit(authorId, post_id, dto);
    }

    @Operation(
            summary = "스터디 정보 수정",
            description = "그룹장이 스터디 이름, 소개, 종료일을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "스터디 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "스터디를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PutMapping("/{studyId}")
    public ResponseEntity<String> updateStudy(
            @Parameter(description = "스터디 ID", required = true)
            @PathVariable int studyId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId,
            @Parameter(description = "스터디 수정 정보", required = true)
            @Valid @RequestBody StudyUpdateDTO request) {
        return studyService.updateStudy(studyId, leaderId, request);
    }

    @Operation(
            summary = "스터디 그룹 삭제",
            description = "그룹장이 그룹원이 한 명도 없을 때 스터디 그룹을 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "스터디 그룹 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (그룹원 존재)"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "스터디를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "그룹원이 있어서 삭제할 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/{studyId}")
    public ResponseEntity<String> deleteStudy(
            @Parameter(description = "삭제할 스터디 ID", required = true)
            @PathVariable int studyId,

            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId) {

        return studyService.deleteStudy(studyId, leaderId);
    }

}
