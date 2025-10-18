package com.threego.algo.study.query.dto;

import com.threego.algo.common.dto.PageableDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyPostSearchDTO extends PageableDTO {

    private int studyId;

    // Builder 패턴 지원 (부모 클래스 필드 포함)
    @Builder
    public StudyPostSearchDTO(Integer page, Integer size, String sortBy, String sortDirection, int studyId) {
        super.setPage(page);
        super.setSize(size);
        super.setSortBy(sortBy);
        super.setSortDirection(sortDirection);
        this.studyId = studyId;
    }

    // 정렬 기준 기본값 오버라이드
    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "createdAt";
    }
}