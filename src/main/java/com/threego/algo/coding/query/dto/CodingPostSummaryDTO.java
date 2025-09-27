package com.threego.algo.coding.query.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
/* 설명. 풀이 게시물 목록*/
public class CodingPostSummaryDTO extends CodingPostDetailDTO {
    private int postId;
    private String postTitle;
    private String postContent;
    private int memberId;
    private String nickname;
    private String memberRank;
    private String problemTitle;
    private int commentCount;
    private int likeCount;
    private String createdAt;
    private String updatedAt;
    private String visibility;

}