package com.threego.algo.coding.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodingPostRequestDTO {
    private int memberId;
    private int problemId;
    private String title;
    private String content;
    private List<MultipartFile> images; // 추가
}
