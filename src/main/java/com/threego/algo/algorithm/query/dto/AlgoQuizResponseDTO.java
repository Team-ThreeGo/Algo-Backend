package com.threego.algo.algorithm.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private int postId;

    private String postTitle;

    private int roadmapId;

    private String roadmapTitle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isSolved;

    private List<AlgoQuizOptionResponseDTO> options;
}