package com.threego.algo.algorithm.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AlgoMemberSolvedQuizResponseDTO {
    private int algoPostId;
    private int algoQuizQuestionId;
    private int algoRoadmapId;
}