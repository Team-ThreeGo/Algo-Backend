package com.threego.algo.studyrecruit.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitDetailDTO {

    private Long id;                    // 모집글 ID
    private String title;               // 제목
    private String content;             // 내용
    private String memberNickname;      // 작성자 닉네임
    private String rankName;            // 작성자 등급명
    private String status;              // 모집 상태
    private String startDate;           // 스터디 시작일
    private String endDate;             // 스터디 종료일
    private String expiresAt;           // 모집 마감일
    private Integer capacity;           // 모집 정원
    private Integer participantCount;   // 현재 참여자 수
    private Integer commentCount;       // 댓글 수
    private String createdAt;           // 작성일
    private String updatedAt;           // 수정일
}