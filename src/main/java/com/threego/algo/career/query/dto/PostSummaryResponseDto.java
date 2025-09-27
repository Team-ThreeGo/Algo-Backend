package com.threego.algo.career.query.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSummaryResponseDto {
    private int id;
    private String title;
    private int memberId;
    private String nickname;
    private String rankName;
    private String createdAt;
    private String status;
    private int likeCount;
    private int commentCount;
}
