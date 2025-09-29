package com.threego.algo.study.command.application.controller;

import com.threego.algo.study.command.application.dto.create.StudyMilestoneCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyMilestoneUpdateDTO;
import com.threego.algo.study.command.application.service.StudyMilestoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(
        name = "Study Milestone - Member Command",
        description = "회원용 스터디 마일스톤 API (Command)"
)
public class StudyMilestoneCommandController {


    private final StudyMilestoneService studyMilestoneService;

    @Operation(summary = "마일스톤 등록", description = "그룹장이 로드맵에 새로운 마일스톤을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "마일스톤 등록 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "로드맵을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/roadmaps/{roadmapId}/milestones")
    public ResponseEntity<String> createMilestone(
            @Parameter(description = "로드맵 ID", required = true)
            @PathVariable int roadmapId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId,
            @Parameter(description = "마일스톤 등록 정보", required = true)
            @Valid @RequestBody StudyMilestoneCreateDTO request) {
        return studyMilestoneService.createMilestone(roadmapId, leaderId, request);
    }

    @Operation(summary = "마일스톤 수정", description = "그룹장이 마일스톤 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마일스톤 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "마일스톤을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PutMapping("/milestones/{milestoneId}")
    public ResponseEntity<String> updateMilestone(
            @Parameter(description = "마일스톤 ID", required = true)
            @PathVariable int milestoneId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId,
            @Parameter(description = "마일스톤 수정 정보", required = true)
            @Valid @RequestBody StudyMilestoneUpdateDTO request) {
        return studyMilestoneService.updateMilestone(milestoneId, leaderId, request);
    }

    @Operation(summary = "마일스톤 삭제", description = "그룹장이 마일스톤을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마일스톤 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "마일스톤을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/milestones/{milestoneId}")
    public ResponseEntity<String> deleteMilestone(
            @Parameter(description = "마일스톤 ID", required = true)
            @PathVariable int milestoneId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId) {
        return studyMilestoneService.deleteMilestone(milestoneId, leaderId);
    }
}
