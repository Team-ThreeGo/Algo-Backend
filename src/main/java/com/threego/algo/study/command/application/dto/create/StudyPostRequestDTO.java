package com.threego.algo.study.command.application.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyPostRequestDTO {
    private Integer studyId;
    private Integer memberId;
    private String title;
    private String content;
    private String visibility;
    private List<MultipartFile> images; // 업로드할 이미지 파일들
}