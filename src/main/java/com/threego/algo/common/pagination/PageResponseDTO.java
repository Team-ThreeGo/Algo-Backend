package com.threego.algo.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDTO<T> {
    private List<T> content;  // 실제 데이터 목록
    private int page;         // 현재 페이지 번호
    private int size;         // 한 페이지 크기
    private long totalCount;  // 전체 데이터 개수

}
