package com.threego.algo.career.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {
    private int id;
    private String title;
    private String content;

    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl; // null이면 json에서 제외

    private int memberId;
    private String nickname;
    private String rankName;
    private String createdAt;
    private String status;
    private String rejectReason;
    private int likeCount;
    private int commentCount;
}
