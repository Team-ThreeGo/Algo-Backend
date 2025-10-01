package com.threego.algo.coding.command.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @JsonProperty("content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
    private List<MultipartFile> images; // 추가
}
