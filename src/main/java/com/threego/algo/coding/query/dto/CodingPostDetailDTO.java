package com.threego.algo.coding.query.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 설명. 풀이 게시물 상세*/
public class CodingPostDetailDTO {
    private int postId;
    private String problemTitle;
    private String problemPlatform;
    private String problemDifficulty;
    private String postTitle;
    private String postContent;
    private String aiBigO;
    private String aiGood;
    private String aiBad;
    private String aiPlan;
    private int commentCount;
    private int likeCount;
    private int memberId;
    private String nickname;
    private String memberRank;
    private String createdAt;
    private String updatedAt;
    private String visibility;
    private List<String> imageUrls;
}
