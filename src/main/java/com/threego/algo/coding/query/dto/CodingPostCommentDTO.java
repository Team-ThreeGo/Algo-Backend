package com.threego.algo.coding.query.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    // 댓글, 대댓글 구분하기 위해 트리 구조
    private List<CodingPostCommentDTO> children = new ArrayList<>();
}
