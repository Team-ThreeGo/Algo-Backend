package com.threego.algo.report.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReportRequest {

    @Schema(description = "신고한 회원 ID", example = "1")
    private int memberId;

    @Schema(description = "카테고리 ID", example = "1")
    private int categoryId;

    @Schema(description = "신고 타입 ID", example = "1")
    private int typeId;

    @Schema(description = "신고 대상 ID (게시물ID, 댓글ID)", example = "10")
    private int targetId;

    @Schema(description = "신고 내용", example = "욕설을 포함하고 있습니다.")
    private String content;
}

