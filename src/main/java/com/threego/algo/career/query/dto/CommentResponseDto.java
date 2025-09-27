package com.threego.algo.career.query.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private int commentId;
    private Integer parentId; // nullable
    private int postId;
    private int memberId;
    private String nickname;
    private String rankName;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String visibility;

    // 대댓글용 children
    @Setter
    private List<CommentResponseDto> children = new ArrayList<>();
}
