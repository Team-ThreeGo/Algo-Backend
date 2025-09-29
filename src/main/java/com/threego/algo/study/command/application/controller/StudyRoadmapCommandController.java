package com.threego.algo.study.command.application.controller;

import com.threego.algo.study.command.application.dto.create.StudyRoadmapCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyRoadmapUpdateDTO;
import com.threego.algo.study.command.application.service.StudyRoadmapService;
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
@RequestMapping("/study/roadmap")
@RequiredArgsConstructor
@Tag(
        name = "Study Roadmap - Member Command",
        description = "회원용 스터디 로드맵 API (Command)"
)
public class StudyRoadmapCommandController {


    private final StudyRoadmapService studyRoadmapService;

    @Operation(summary = "로드맵 등록", description = "그룹장이 스터디에 새로운 로드맵을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "로드맵 등록 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "스터디를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/{studyId}/roadmaps")
    public ResponseEntity<String> createRoadmap(
            @Parameter(description = "스터디 ID", required = true)
            @PathVariable int studyId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId,
            @Parameter(description = "로드맵 등록 정보", required = true)
            @Valid @RequestBody StudyRoadmapCreateDTO request) {
        return studyRoadmapService.createRoadmap(studyId, leaderId, request);
    }

    @Operation(summary = "로드맵 수정", description = "그룹장이 로드맵 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로드맵 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "로드맵을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PutMapping("/roadmaps/{roadmapId}")
    public ResponseEntity<String> updateRoadmap(
            @Parameter(description = "로드맵 ID", required = true)
            @PathVariable int roadmapId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId,
            @Parameter(description = "로드맵 수정 정보", required = true)
            @Valid @RequestBody StudyRoadmapUpdateDTO request) {
        return studyRoadmapService.updateRoadmap(roadmapId, leaderId, request);
    }

    @Operation(summary = "로드맵 삭제", description = "그룹장이 로드맵을 삭제합니다. (하위 마일스톤이 없어야 함)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로드맵 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "하위 마일스톤 존재로 삭제 불가"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (그룹장이 아님)"),
            @ApiResponse(responseCode = "404", description = "로드맵을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/roadmaps/{roadmapId}")
    public ResponseEntity<String> deleteRoadmap(
            @Parameter(description = "로드맵 ID", required = true)
            @PathVariable int roadmapId,
            @Parameter(description = "그룹장 ID", required = true)
            @RequestHeader("Member-Id") int leaderId) {
        return studyRoadmapService.deleteRoadmap(roadmapId, leaderId);
    }
}