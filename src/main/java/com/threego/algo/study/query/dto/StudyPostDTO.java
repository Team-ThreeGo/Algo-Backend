package com.threego.algo.study.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPostDTO {
    private Integer id;
    private Integer studyId;
    private Integer memberId;
    private String memberNickname;
    private String title;
    private String content;
    private Integer commentCount;
    private String createdAt;
    private String updatedAt;
    private String visibility;
}