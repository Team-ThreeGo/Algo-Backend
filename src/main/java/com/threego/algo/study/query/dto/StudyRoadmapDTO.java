package com.threego.algo.study.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoadmapDTO {
    private Integer id;
    private Integer studyId;
    private Integer memberId;
    private String title;
    private String description;
    private Integer order;
    private String createdAt;
}