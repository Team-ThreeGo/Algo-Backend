package com.threego.algo.coding.query.dto;

import com.threego.algo.common.dto.PageableDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodingPostSearchConditionDTO extends PageableDTO {
    private String keyword;      // 제목/내용/닉네임 검색
    private String visibility;   // Y / N
    private Integer problemId;

    // Builder 패턴 지원 (부모 클래스 필드 포함)
    @Builder
    public CodingPostSearchConditionDTO(Integer page, Integer size, String sortBy, String sortDirection,
                                         String keyword, String visibility, Integer problemId) {
        super.setPage(page);
        super.setSize(size);
        super.setSortBy(sortBy);
        super.setSortDirection(sortDirection);
        this.keyword = keyword;
        this.visibility = visibility;
        this.problemId = problemId;
    }

    // 정렬 기준 기본값 오버라이드
    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "id";
    }
}