package com.threego.algo.career.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CareerCommentRequest {
    private String content;
    private Integer parentId;
}
