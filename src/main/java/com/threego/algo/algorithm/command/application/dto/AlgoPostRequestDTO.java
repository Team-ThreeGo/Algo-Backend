package com.threego.algo.algorithm.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AlgoPostRequestDTO {
    private String title;
    private String content;
    private List<String> imageUrls;
    private List<MultipartFile> images;
}