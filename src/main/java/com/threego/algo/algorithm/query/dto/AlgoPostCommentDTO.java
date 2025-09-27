package com.threego.algo.algorithm.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AlgoPostCommentDTO {
    private int id;

    private Integer parentCommentId;

    private int memberId;

    private String memberNickname;

    private String memberRank;

    private String content;

    private String createdAt;

    private String updatedAt;

    private char visibility;

    private List<AlgoPostCommentDTO> childComments;
}