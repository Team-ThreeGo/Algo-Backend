package com.threego.algo.study.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberDTO {
    private Integer id;
    private Integer studyId;
    private Integer memberId;
    private String memberNickname;  // Member 테이블에서 가져올 닉네임
    private String memberRank;
    private StudyMemberRole role;  // LEADER, MEMBER, NOT_MEMBER

    @Getter
    public enum StudyMemberRole {
        LEADER("LEADER", "리더"),
        MEMBER("MEMBER", "멤버"),
        NOT_MEMBER("NOT_MEMBER", "탈퇴/추방 멤버");

        private final String role;
        private final String description;

        StudyMemberRole(String role, String description) {
            this.role = role;
            this.description = description;
        }
    }
}
