package com.threego.algo.common.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDTO {
    private int page = 1;   // 현재 페이지 번호 (기본값 1)
    private int size = 10;  // 페이지당 데이터 개수 (기본값 10)

    public int getOffset() {
        return (page - 1) * size;
    }
}
