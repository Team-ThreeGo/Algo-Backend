package com.threego.algo.studyrecruit.query.dto;

import com.threego.algo.common.dto.PageableDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitSearchDTO extends PageableDTO {

    private String keyword;             // 검색 키워드 (제목, 내용)
    private String status;              // 모집 상태 필터 (OPEN, CLOSED, CANCELLED)
    private String memberNickname;      // 작성자 닉네임
    private String startDateFrom;       // 시작일 범위 검색 (시작)
    private String startDateTo;         // 시작일 범위 검색 (끝)
    private String expiresAtFrom;       // 모집마감일 범위 검색 (시작)
    private String expiresAtTo;         // 모집마감일 범위 검색 (끝)
    private int minCapacity;            // 최소 모집 정원
    private int maxCapacity;            // 최대 모집 정원

    // Builder 패턴 지원 (부모 클래스 필드 포함)
    @Builder
    public StudyRecruitSearchDTO(Integer page, Integer size, String sortBy, String sortDirection,
                                  String keyword, String status, String memberNickname,
                                  String startDateFrom, String startDateTo,
                                  String expiresAtFrom, String expiresAtTo,
                                  int minCapacity, int maxCapacity) {
        super.setPage(page);
        super.setSize(size);
        super.setSortBy(sortBy);
        super.setSortDirection(sortDirection);
        this.keyword = keyword;
        this.status = status;
        this.memberNickname = memberNickname;
        this.startDateFrom = startDateFrom;
        this.startDateTo = startDateTo;
        this.expiresAtFrom = expiresAtFrom;
        this.expiresAtTo = expiresAtTo;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    // 정렬 기준 기본값 오버라이드
    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "createdAt";
    }
}