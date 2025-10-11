package com.threego.algo.coding.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodingPostSearchConditionDTO {
    private String keyword;      // 제목/내용/닉네임 검색
    private String visibility;   // Y / N
    private Integer problemId;
    private int limit;
    private int offset;  // MyBatis에서 자동 계산될 값
}