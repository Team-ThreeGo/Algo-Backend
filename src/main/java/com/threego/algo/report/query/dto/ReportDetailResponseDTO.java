package com.threego.algo.report.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailResponseDTO {
    int id;
    int memberId;
    String reporter;
    int categoryId;
    String categoryName;
    int typeId;
    String typeName;
    String createdAt;
    String content;
    int targetId;
    int reportedMemberId;
    String reported;
}
