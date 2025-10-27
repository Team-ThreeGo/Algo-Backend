package com.threego.algo.algorithm.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AlgoRoadmapRequestDTO {
    private String title;
    private String description;
    private Integer order;
}