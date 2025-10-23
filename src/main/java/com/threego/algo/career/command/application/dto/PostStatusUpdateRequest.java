package com.threego.algo.career.command.application.dto;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostStatusUpdateRequest {
    private Status status;
    private String rejectReason;
}
