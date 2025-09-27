package com.threego.algo.study.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPostSearchDTO {
    private Integer studyId;
    private Integer page;
    private Integer size;
    private Integer offset;  // MyBatis에서 자동 계산될 값
}