package com.threego.algo.study.query.controller;

import com.threego.algo.study.query.dto.StudyRoadmapDTO;
import com.threego.algo.study.query.dto.StudyRoadmapDetailDTO;
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
        name = "Study Roadmap - Member Query",
        description = "회원용 스터디 로드맵 API (Query)"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyRoadmapQueryController {

    private final StudyRoadmapQueryService studyRoadmapQueryService;

    @Operation(
            summary = "스터디 로드맵 목록 조회",
            description = "스터디 그룹의 전체 로드맵 목록을 조회합니다."
    )
    @GetMapping("/{studyId}/roadmaps")
    public ResponseEntity<List<StudyRoadmapDTO>> findRoadmaps(@PathVariable int studyId) {
        List<StudyRoadmapDTO> roadmaps = studyRoadmapQueryService.findAllStudyRoadmap(studyId);
        return ResponseEntity.ok(roadmaps);
    }

    @Operation(
            summary = "스터디 로드맵 상세 조회",
            description = "스터디 로드맵의 상세 정보와 마일스톤 목록을 조회합니다."
    )
    @GetMapping("/roadmaps/{roadmapId}")
    public ResponseEntity<StudyRoadmapDetailDTO> findRoadmapDetail(@PathVariable int roadmapId) {
        StudyRoadmapDetailDTO roadmapDetail = studyRoadmapQueryService.findStudyRoadmapDetail(roadmapId);
        return ResponseEntity.ok(roadmapDetail);
    }
}
