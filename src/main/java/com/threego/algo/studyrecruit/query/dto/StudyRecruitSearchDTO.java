package com.threego.algo.studyrecruit.query.dto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitSearchDTO {

    private String keyword;             // 검색 키워드 (제목, 내용)
    private String status;              // 모집 상태 필터 (OPEN, CLOSED, CANCELLED)
    private String memberNickname;      // 작성자 닉네임
    private String startDateFrom;       // 시작일 범위 검색 (시작)
    private String startDateTo;         // 시작일 범위 검색 (끝)
    private String expiresAtFrom;       // 모집마감일 범위 검색 (시작)
    private String expiresAtTo;         // 모집마감일 범위 검색 (끝)
    private Integer minCapacity;        // 최소 모집 정원
    private Integer maxCapacity;        // 최대 모집 정원

    // 페이징 정보
    private Integer page;               // 페이지 번호 (0부터 시작)
    private Integer size;               // 페이지 크기
    private String sortBy;              // 정렬 기준 (createdAt, startDate, expiresAt)
    private String sortDirection;       // 정렬 방향 (ASC, DESC)

    // 기본값 설정
    public Integer getPage() {
        return page != null ? page : 0;
    }

    public Integer getSize() {
        return size != null ? size : 10;
    }

    public String getSortBy() {
        return sortBy != null ? sortBy : "createdAt";
    }

    public String getSortDirection() {
        return sortDirection != null ? sortDirection : "DESC";
    }

    // OFFSET 계산
    public Integer getOffset() {
        return getPage() * getSize();
    }
}