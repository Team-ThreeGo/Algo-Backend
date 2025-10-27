package com.threego.algo.study.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyDTO {
    private int id;                    // 스터디 ID
    private int recruitPostId;         // 원본 모집글 ID
    private String name;               // 스터디 이름
    private String description;        // 스터디 설명
    private String startDate;          // 스터디 시작일
    private String endDate;            // 스터디 종료일
}
