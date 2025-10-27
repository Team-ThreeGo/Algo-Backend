package com.threego.algo.file.command.application.controller;

import com.threego.algo.common.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart(value = "image")final MultipartFile file) {
        s3Service.validateImageFile(file);

        final String s3Url = s3Service.uploadFile(file, "temp");

        return ResponseEntity.ok().body(s3Url);
    }
}