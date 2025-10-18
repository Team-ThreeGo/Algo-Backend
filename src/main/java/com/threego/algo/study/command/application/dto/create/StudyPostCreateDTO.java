package com.threego.algo.study.command.application.dto.create;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class StudyPostCreateDTO {
    private String title;
    private String content;

    public StudyPostCreateDTO() {}  // 필수!

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}