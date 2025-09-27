package com.threego.algo.algorithm.query.dto;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlgoRoadmapResponseDTO {
    @Schema(description = "알고리즘 학습 로드맵 ID")
    private final int id;

    @Schema(description = "알고리즘 학습 로드맵 제목", defaultValue = "자료구조 기초")
    private final String title;

    @Schema(description = "알고리즘 학습 로드맵 설명",
            defaultValue = "스택, 큐, 리스트와 같은 기본 자료구조를 이해하는 것은 알고리즘 학습의 출발점입니다.")
    private final String description;

    @Schema(description = "알고리즘 학습 로드맵 순서", defaultValue = "1")
    private final int order;

    @Schema(description = "알고리즘 학습 로드맵 생성일시", defaultValue = "2025-09-02 09:00:00")
    private final String createdAt;

    @Schema(description = "알고리즘 학습 로드맵 수정일시", defaultValue = "2025-09-02 10:00:00")
    private final String updatedAt;

    @Schema(description = "알고리즘 학습 로드맵 총 문제 개수", defaultValue = "2")
    private final int questionCount;

    public static AlgoRoadmapResponseDTO of(final AlgoRoadmap algoRoadmap) {
        return AlgoRoadmapResponseDTO.builder()
                .id(algoRoadmap.getId())
                .title(algoRoadmap.getTitle())
                .description(algoRoadmap.getDescription())
                .order(algoRoadmap.getOrder())
                .createdAt(algoRoadmap.getCreatedAt())
                .updatedAt(algoRoadmap.getUpdatedAt())
                .questionCount(algoRoadmap.getQuestionCount())
                .build();
    }
}