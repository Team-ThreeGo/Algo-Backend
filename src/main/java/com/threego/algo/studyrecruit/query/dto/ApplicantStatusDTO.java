package com.threego.algo.studyrecruit.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantStatusDTO {
    private String status;  // PENDING, APPROVED, REJECTED
}
