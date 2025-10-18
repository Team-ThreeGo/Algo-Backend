package com.threego.algo.studyrecruit.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitPostListResponseDTO {
    private List<StudyRecruitPostDTO> posts;  // 게시물 목록
    private long totalCount;                   // 전체 게시물 수
}
