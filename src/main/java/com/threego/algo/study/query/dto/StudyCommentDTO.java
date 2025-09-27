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
public class StudyCommentDTO {
    private Integer id;
    private Integer postId;
    private Integer memberId;
    private String memberNickname;
    private Integer parentId;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String visibility;
    private List<StudyCommentDTO> replies; // 대댓글 리스트
}