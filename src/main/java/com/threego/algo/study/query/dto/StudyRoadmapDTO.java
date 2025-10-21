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
    private int id;
    private int studyId;
    private int memberId;
    private String title;
    private String description;
    private int order;
    private String createdAt;
}