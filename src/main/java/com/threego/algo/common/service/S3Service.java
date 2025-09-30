package com.threego.algo.common.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
//    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region; // region을 직접 주입받기

    public String uploadFile(MultipartFile file, String folder) {
        try {
            String fileName = createFileName(file.getOriginalFilename(), folder);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return getFileUrl(fileName);

        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드에 실패했습니다.");
        }
    }




    public void deleteFile(String fileUrl) {
        try {
            String fileName = extractFileNameFromUrl(fileUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new IllegalArgumentException("파일 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    public boolean fileExists(String fileUrl) {
        try {
            String fileName = extractFileNameFromUrl(fileUrl);

            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 수정된 getFileUrl 메서드
    private String getFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,  // 직접 주입받은 region 사용
                fileName);
    }

    public String uploadStudyPostImage(MultipartFile file) {
        return uploadFile(file, "study-posts");
    }

    public boolean isValidImageFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        if (!fileName.contains(".")) {
            return false;
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "bmp").contains(extension);
    }

    public boolean isValidFileSize(MultipartFile file) {
        return file.getSize() <= 5 * 1024 * 1024;
    }

    public boolean isValidFileSize(MultipartFile file, long maxSizeInMB) {
        return file.getSize() <= maxSizeInMB * 1024 * 1024;
    }

    public boolean isValidImageMimeType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("image/");
    }

    public void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        if (!isValidImageFile(file)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다. (JPG, JPEG, PNG, GIF, WEBP, BMP만 가능)");
        }

        if (!isValidImageMimeType(file)) {
            throw new IllegalArgumentException("올바른 이미지 파일이 아닙니다.");
        }

        if (!isValidFileSize(file)) {
            throw new IllegalArgumentException("이미지 파일 크기는 5MB 이하여야 합니다.");
        }
    }

    private String createFileName(String originalFileName, String folder) {
        return folder + "/" + UUID.randomUUID().toString() + "_" + originalFileName;
    }

    private String extractFileNameFromUrl(String fileUrl) {
        try {
            String[] parts = fileUrl.split(".com/");
            if (parts.length > 1) {
                return parts[1];
            }
            return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("URL에서 파일명을 추출할 수 없습니다: " + fileUrl);
        }
    }
}