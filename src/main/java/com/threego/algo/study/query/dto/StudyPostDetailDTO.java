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
public class StudyPostDetailDTO {
    private Integer id;
    private Integer studyId;
    private Integer memberId;
    private String memberNickname;
    private String title;
    private String content;
    private Integer commentCount;
    private String createdAt;
    private String updatedAt;
    private String visibility;
    private List<PostImageInfo> images;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostImageInfo {
        private Integer imageId;
        private String imageUrl;
        private String createdAt;
    }
}