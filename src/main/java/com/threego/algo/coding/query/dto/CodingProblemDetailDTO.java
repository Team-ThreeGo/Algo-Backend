package com.threego.algo.coding.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 설명. 코딩문제 상세*/
public class CodingProblemDetailDTO {
    private int problemId;
    private int memberId;
    private String nickname;
    private String problemTitle;
    private String content;
    private String platform;
    private String difficulty;
    private int postCount;
    private String input;
    private String output;
    private String problemUrl;
    private String createdAt;
    private String visibility;

    // === AI 피드백 추가 ===
    private String aiBigO;
    private String aiGood;
    private String aiBad;
    private String aiPlan;
}
