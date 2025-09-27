package com.threego.algo.coding.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 설명. 코딩문제 목록*/
public class CodingProblemSummaryDTO {
    private int problemId;
    private int memberId;
    private String nickname;
    private String problemTitle;
    private String platform;
    private String difficulty;
    private int postCount;
    private String createdAt;
    private String visibility;
}
