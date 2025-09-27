package com.threego.algo.coding.query.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 설명. 풀이 게시물 댓글*/
public class CodingPostCommentDTO {
    private int postId;
    private int commentId;
    private Integer parentId;
    private int memberId;
    private String nickname;
    private String memberRank;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String visibility;
}
