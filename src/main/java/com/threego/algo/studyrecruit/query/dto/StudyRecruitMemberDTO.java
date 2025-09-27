package com.threego.algo.studyrecruit.query.dto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitMemberDTO {

    private Long id;                    // 신청 ID
    private Long memberId;              // 신청자 회원 ID
    private String memberNickname;      // 신청자 닉네임
    private String rankName;            // 신청자 등급명
    private String status;              // 신청 상태 (PENDING, APPROVED, REJECTED)
    private String appliedAt;           // 신청일 (created_at 사용)

    /* 설명. 승인 여부 확인 */
    public boolean isApproved() {
        return "APPROVED".equals(this.status);
    }

    /* 설명. 대기 여부 확인 */
    public boolean isPending() {
        return "PENDING".equals(this.status);
    }
}