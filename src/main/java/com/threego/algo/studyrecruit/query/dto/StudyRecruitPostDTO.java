package com.threego.algo.studyrecruit.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitPostDTO {
    private Long id;                    // 모집글 ID
    private String title;               // 제목
    private String memberNickname;      // 작성자 닉네임
    private String rankName;            // 작성자 등급명
    private Status status;              // 모집 상태 (OPEN, CLOSED, CANCELLED)
    private String startDate;           // 스터디 시작일
    private String endDate;             // 스터디 종료일
    private String expiresAt;           // 모집 마감일
    private Integer capacity;           // 모집 정원
    private Integer participantCount;   // 현재 참여자 수 (APPROVED 상태만)
    private Integer commentCount;       // 댓글 수
    private String createdAt;           // 작성일
    private String updatedAt;           // 수정일

    @Getter
    public enum Status {
        OPEN("OPEN", "모집 중"),
        CLOSED("CLOSED", "모집 마감"),
        CANCELLED("CANCELLED", "모집 취소");

        private String status;
        private String description;
        Status(String status, String description) {
            this.status = status;
            this.description = description;
        }
    }
}
