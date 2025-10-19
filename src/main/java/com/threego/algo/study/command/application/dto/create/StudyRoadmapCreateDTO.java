package com.threego.algo.study.command.application.dto.create;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoadmapCreateDTO {

    @NotBlank(message = "로드맵 제목은 필수입니다.")
    @Size(max = 100, message = "로드맵 제목은 100자 이하여야 합니다.")
    private String title;

    @Size(max = 500, message = "로드맵 설명은 500자 이하여야 합니다.")
    private String description;

    @Min(value = 1, message = "순서는 1 이상이어야 합니다.")
    private int order;
}