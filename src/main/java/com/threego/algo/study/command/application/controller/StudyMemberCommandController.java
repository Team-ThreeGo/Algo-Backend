package com.threego.algo.study.command.application.controller;

import com.threego.algo.common.auth.LoginMember;
import com.threego.algo.study.command.application.dto.create.StudyCommentCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyCommentUpdateDTO;
import com.threego.algo.study.command.application.service.StudyCommentService;
import com.threego.algo.study.command.application.service.StudyMemberService;
import com.threego.algo.study.command.application.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(
        name = "Study Member - Member Command",
        description = "회원용 스터디 멤버 API (Command)"
)
public class StudyMemberCommandController {

    private final StudyMemberService studyMemberService;

    @Operation(
            summary = "그룹원 강퇴",
            description = "스터디 그룹장이 그룹원을 강퇴합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "그룹원 강퇴 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (권한 없음, 존재하지 않는 멤버 등)"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "스터디 또는 멤버를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/{studyId}/members/{memberId}")
    public ResponseEntity<String> kickMember(
            @Parameter(description = "스터디 ID", required = true)
            @PathVariable int studyId,

            @Parameter(description = "강퇴할 멤버 ID", required = true)
            @PathVariable int memberId,
            @Parameter(hidden = true) @LoginMember int leaderId) {

        return studyMemberService.kickMember(studyId, memberId, leaderId);
    }

    @Operation(
            summary = "그룹장 권한 위임",
            description = "현재 그룹장이 다른 그룹원에게 그룹장 권한을 위임합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "권한 위임 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (존재하지 않는 멤버 등)"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "스터디 또는 멤버를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PutMapping("/{studyId}/leader/{memberId}")
    public ResponseEntity<String> delegateLeadership(
            @Parameter(description = "스터디 ID", required = true)
            @PathVariable int studyId,

            @Parameter(description = "새로운 그룹장이 될 멤버 ID", required = true)
            @PathVariable int memberId,
            @Parameter(hidden = true) @LoginMember int currentLeaderId) {

        return studyMemberService.delegateLeadership(studyId, memberId, currentLeaderId);
    }
}
