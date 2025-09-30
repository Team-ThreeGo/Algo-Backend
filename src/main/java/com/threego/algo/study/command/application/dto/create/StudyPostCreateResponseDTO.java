package com.threego.algo.study.command.application.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StudyPostCreateResponseDTO {
    private Integer id;
    private Integer studyId;
    private Integer memberId;
    private String title;
    private String content;
    private Integer commentCount;
    private String createdAt;
    private String updatedAt;
    private String visibility;
    private List<String> imageUrls; // S3에 저장된 이미지 URL 리스트
}
