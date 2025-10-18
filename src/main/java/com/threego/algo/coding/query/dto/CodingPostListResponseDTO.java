package com.threego.algo.coding.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodingPostListResponseDTO {
    private List<CodingPostSummaryDTO> posts;
    private long totalCount;
}
