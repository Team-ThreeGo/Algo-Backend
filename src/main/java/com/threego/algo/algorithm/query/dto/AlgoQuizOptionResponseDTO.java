package com.threego.algo.algorithm.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AlgoQuizOptionResponseDTO {
    private int id;

    private String optionText;

    private boolean isCorrect;
}