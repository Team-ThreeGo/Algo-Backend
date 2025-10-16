package com.threego.algo.coding.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodingPostImageRequestDTO {
//    private MultipartFile file; // 실제 업로드 파일
    private MultipartFile image;
}