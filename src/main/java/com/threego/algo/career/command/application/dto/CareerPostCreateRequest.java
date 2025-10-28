package com.threego.algo.career.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CareerPostCreateRequest {
    private String title;
    private String content;
    private String company;
    private String year;
    private MultipartFile image; // 단일 파일
}
