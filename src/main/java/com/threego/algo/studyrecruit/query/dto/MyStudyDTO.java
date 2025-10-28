package com.threego.algo.studyrecruit.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyStudyDTO {
    private int studyId;              // 스터디 ID
    private String studyName;          // 스터디 이름
    private String startDate;          // 스터디 시작일
    private String endDate;            // 스터디 종료일
    private int memberCount;           // 참여 멤버 수
    private String leaderName;         // 스터디장 이름
    private String leaderRank;         // 스터디장 랭크
}
