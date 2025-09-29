package com.threego.algo.studyrecruit.command.application.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecruitReportCreateDTO {

    @NotBlank(message = "신고 사유는 필수입니다.")
    @Size(max = 500, message = "신고 사유는 500자 이하여야 합니다.")
    private String reason;
}