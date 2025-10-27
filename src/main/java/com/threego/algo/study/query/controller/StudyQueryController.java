package com.threego.algo.study.query.controller;

import com.threego.algo.study.query.dto.StudyDTO;
import com.threego.algo.study.query.service.StudyQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(
        name = "Study - Query",
        description = "스터디 조회 API (Query)"
)
public class StudyQueryController {

    private final StudyQueryService studyQueryService;

    @Operation(
            summary = "스터디 정보 조회",
            description = "스터디 ID로 스터디 정보를 조회합니다. (배너용)"
    )
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyDTO> findStudy(@PathVariable int studyId) {
        return ResponseEntity.ok(studyQueryService.findStudyById(studyId));
    }
}
