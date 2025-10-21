package com.threego.algo.career.query.dto;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.common.dto.PageableDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareerPostSearchDTO extends PageableDTO {

    private String visibility;
    private Status status;
    private String keyword;

    // Builder 패턴 지원 (부모 클래스 필드 포함)
    @Builder
    public CareerPostSearchDTO(Integer page, Integer size, String sortBy, String sortDirection,
                                String visibility, Status status, String keyword) {
        super.setPage(page);
        super.setSize(size);
        super.setSortBy(sortBy);
        super.setSortDirection(sortDirection);
        this.visibility = visibility;
        this.status = status;
        this.keyword = keyword;
    }

    // 정렬 기준 기본값 오버라이드
    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "id";
    }
}
