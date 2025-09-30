package com.threego.algo.coding.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class CodingPostImageRequestDTO {
    private String imageUrl;
    private MultipartFile file; // 실제 업로드 파일
}