package com.threego.algo.study.command.application.dto.update;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMilestoneUpdateDTO {

    @NotBlank(message = "마일스톤 제목은 필수입니다.")
    @Size(max = 100, message = "마일스톤 제목은 100자 이하여야 합니다.")
    private String title;

    @Size(max = 500, message = "마일스톤 설명은 500자 이하여야 합니다.")
    private String description;

    @NotNull(message = "순서는 필수입니다.")
    @Min(value = 1, message = "순서는 1 이상이어야 합니다.")
    private int order;
}