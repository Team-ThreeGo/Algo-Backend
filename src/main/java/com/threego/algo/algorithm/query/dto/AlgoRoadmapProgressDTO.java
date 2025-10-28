package com.threego.algo.algorithm.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlgoRoadmapProgressDTO {
    private int totalQuestions;          // 전체 문제 수
    private int solvedQuestions;         // 해결한 문제 수
    private double progressRate;         // 진행률 (0.0 ~ 1.0)
}
