package com.threego.algo.coding.query.dto;

import com.threego.algo.common.dto.PageableDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodingPostSearchConditionDTO extends PageableDTO {
    private String keyword;      // 제목/내용/닉네임 검색
    private String visibility;   // Y / N
    private Integer problemId;
}