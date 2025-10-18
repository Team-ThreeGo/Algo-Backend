package com.threego.algo.study.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyPostListResponseDTO {
    private List<StudyPostDTO> posts;  // 게시물 목록
    private long totalCount;            // 전체 게시물 수
}
