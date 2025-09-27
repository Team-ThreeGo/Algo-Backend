package com.threego.algo.algorithm.query.dto;

import com.threego.algo.algorithm.command.domain.aggregate.enums.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AlgoQuizResponseDTO {
    private int id;

    private String question;

    private Type type;

    private List<AlgoQuizOptionResponseDTO> options;
}