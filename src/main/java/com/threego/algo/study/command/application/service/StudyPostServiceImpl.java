package com.threego.algo.study.command.application.service;

import com.threego.algo.common.service.S3Service;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRole;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.member.command.domain.repository.MemberRoleRepository;
import com.threego.algo.study.command.application.dto.create.StudyPostCreateDTO;
import com.threego.algo.study.command.application.dto.create.StudyPostCreateResponseDTO;
import com.threego.algo.study.command.application.dto.create.StudyPostRequestDTO;
import com.threego.algo.study.command.application.dto.create.StudyReportCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyPostUpdateDTO;
import com.threego.algo.study.command.domain.aggregate.Study;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.aggregate.StudyPost;
import com.threego.algo.study.command.domain.aggregate.StudyPostImage;
import com.threego.algo.study.command.domain.aggregate.enums.StudyRole;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.command.domain.repository.StudyPostImageRepository;
import com.threego.algo.study.command.domain.repository.StudyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyPostServiceImpl implements StudyPostService {

    private final StudyPostRepository studyPostRepository;
    private final StudyPostImageRepository studyPostImageRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final S3Service s3Service;

    public StudyPostCreateResponseDTO createPost(StudyPostRequestDTO requestDto) {
        // 1. 게시물 저장
        StudyPost post = StudyPost.builder()
                .studyId(requestDto.getStudyId())
                .memberId(requestDto.getMemberId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .commentCount(0)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .visibility(requestDto.getVisibility() != null ? requestDto.getVisibility() : "Y")
                .build();

        StudyPost savedPost = studyPostRepository.save(post);

        // 2. 이미지 업로드 및 저장
        List<String> imageUrls = new ArrayList<>();
        if (requestDto.getImages() != null && !requestDto.getImages().isEmpty()) {
            for (MultipartFile image : requestDto.getImages()) {
                if (!image.isEmpty()) {
                    // 이미지 파일 유효성 검사
                    s3Service.validateImageFile(image);

                    // S3에 업로드 (study-posts 폴더에 저장)
                    String imageUrl = s3Service.uploadStudyPostImage(image);

                    // DB에 저장
                    StudyPostImage postImage = StudyPostImage.builder()
                            .postId(savedPost.getId())
                            .imageUrl(imageUrl)
                            .build();
                    studyPostImageRepository.save(postImage);

                    imageUrls.add(imageUrl);
                }
            }
        }

        // 3. 응답 생성
        return StudyPostCreateResponseDTO.builder()
                .id(savedPost.getId())
                .studyId(savedPost.getStudyId())
                .memberId(savedPost.getMemberId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .commentCount(savedPost.getCommentCount())
                .createdAt(savedPost.getCreatedAt())
                .visibility(savedPost.getVisibility())
                .imageUrls(imageUrls)
                .build();
    }


    @Override
    public ResponseEntity<String> updatePost(int postId, int memberId, StudyPostUpdateDTO request) {
        try {
            // 1. 게시물 존재 여부 확인
            StudyPost post = studyPostRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

            // 2. 작성자 권한 확인
            if (post.getMemberId() != (memberId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("게시물을 수정할 권한이 없습니다.");
            }

            // 3. 게시물 수정
            post.updatePost(request.getTitle(), request.getContent());

            return ResponseEntity.ok("게시물이 성공적으로 수정되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시물 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> deletePost(int postId, int memberId) {
        try {
            // 1. 게시물 존재 여부 확인
            StudyPost post = studyPostRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

            // 2. 작성자 권한 확인
            if (post.getMemberId() != (memberId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("게시물을 삭제할 권한이 없습니다.");
            }

            // 3. 소프트 딜리트
            post.softDelete();

            return ResponseEntity.ok("게시물이 성공적으로 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시물 삭제 중 오류가 발생했습니다.");
        }
    }

    private void rollbackUploadedImages(int postId) {
        try {
            List<StudyPostImage> uploadedImages = studyPostImageRepository.findByPostId((postId));
            for (StudyPostImage image : uploadedImages) {
                try {
                    s3Service.deleteFile(image.getImageUrl());
                } catch (Exception e) {
                    System.err.println("롤백 중 S3 파일 삭제 실패: " + image.getImageUrl());
                }
            }
            studyPostImageRepository.deleteByPostId(postId);
        } catch (Exception e) {
            System.err.println("이미지 롤백 처리 중 오류 발생: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> adminDeletePost(int postId, int adminId) {
        try {
            // 1. 관리자 권한 확인
            if (!isAdmin(adminId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
            }

            // 2. 스터디 게시물 존재 여부 확인
            StudyPost post = studyPostRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("스터디 게시물을 찾을 수 없습니다."));

            // 3. 소프트 딜리트 (VISIBILITY: Y → N)
            post.softDelete();
            studyPostRepository.save(post);

            return ResponseEntity.ok("스터디 게시물이 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스터디 게시물 삭제 중 오류가 발생했습니다.");
        }
    }

    private boolean isAdmin(int memberId) {
        try {
            int roleId = memberRoleRepository.getRoleIdByMemberId(memberId);
            return roleId == 2;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateStudyMemberAccess(int studyId, int memberId) {
        StudyMember studyMember = getStudyMember(studyId, memberId);

        if (!studyMember.isActive()) {
            throw new IllegalArgumentException("스터디 접근 권한이 없습니다.");
        }
    }

    private StudyMember getStudyMember(int studyId, int memberId) {
        return (StudyMember) studyMemberRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("스터디 멤버가 아닙니다."));
    }

}
