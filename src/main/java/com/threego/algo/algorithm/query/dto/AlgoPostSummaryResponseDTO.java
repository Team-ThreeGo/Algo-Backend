package com.threego.algo.algorithm.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AlgoPostSummaryResponseDTO {
    @Schema(description = "알고리즘 학습 게시물 ID")
    private int postId;

    @Schema(description = "알고리즘 학습 로드맵 ID")
    private int roadmapId;

    @Schema(description = "알고리즘 학습 게시물 작성자 ID")
    private int memberId;

    @Schema(description = "알고리즘 학습 게시물 작성자 닉넥임")
    private String nickname;

    @Schema(description = "알고리즘 학습 게시물 제목")
    private String title;

    @Schema(description = "알고리즘 학습 게시물 생성일시")
    private String createdAt;

    @Schema(description = "알고리즘 학습 게시물 수정일시")
    private String updatedAt;

    @Schema(description = "알고리즘 학습 게시물 추천 수")
    private int likeCount;

    @Schema(description = "알고리즘 학습 게시물 댓글 수")
    private int commentCount;

    @Schema(description = "알고리즘 학습 게시물 삭제 여부")
    private char visibility;

    @Schema(description = "알고리즘 학습 게시물 추천 여부")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLiked;

    @Schema(description = "알고리즘 학습 게시물 퀴즈 ID 리스트")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> quizIds;

    @Schema(description = "회원이 맞힌 알고리즘 학습 게시물 퀴즈 ID 리스트")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> solvedQuizIds;
}