package com.threego.algo.study.command.application.controller;

import com.threego.algo.common.auth.LoginMember;
import com.threego.algo.study.command.application.dto.create.StudyPostCreateResponseDTO;
import com.threego.algo.study.command.application.dto.create.StudyPostRequestDTO;
import com.threego.algo.study.command.application.dto.update.StudyPostUpdateDTO;
import com.threego.algo.study.command.application.service.StudyPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(
        name = "Study Post - Member Command",
        description = "회원용 스터디 게시물 API (Command)"
)
public class StudyPostCommandController {

    private final StudyPostService studyPostService;

    @PostMapping(value = "/{studyId}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "스터디 게시물 등록",
               description = "form-data로 title, content, visibility와 이미지 파일들을 전송")
    public ResponseEntity<StudyPostCreateResponseDTO> createPost(
            @Parameter(description = "스터디 ID") @PathVariable int studyId,
            @Parameter(hidden = true) @LoginMember int memberId,
            @Parameter(description = "제목") @RequestParam String title,
            @Parameter(description = "내용") @RequestParam String content,
            @Parameter(description = "공개 여부 (기본값: Y)") @RequestParam(required = false, defaultValue = "Y") String visibility,
            @Parameter(description = "이미지 파일들 (최대 5MB, JPG/PNG/GIF 등)")
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        StudyPostRequestDTO requestDto = new StudyPostRequestDTO();
        requestDto.setStudyId(studyId);
        requestDto.setMemberId(memberId);
        requestDto.setTitle(title);
        requestDto.setContent(content);
        requestDto.setVisibility(visibility);
        requestDto.setImages(images);

        StudyPostCreateResponseDTO response = studyPostService.createPost(requestDto);
        return ResponseEntity.ok(response);
    }

    // 게시물 수정
    @PutMapping("/posts/{postId}")
    @Operation(summary = "스터디 게시물 수정", description = "게시물 작성자가 자신의 게시물을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터 또는 존재하지 않는 게시물"),
            @ApiResponse(responseCode = "403", description = "게시물 수정 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<String> updatePost(
            @PathVariable int postId,
            @RequestBody StudyPostUpdateDTO postDto,
            @Parameter(hidden = true) @LoginMember int memberId) {
        return studyPostService.updatePost(postId, memberId, postDto);
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "스터디 게시물 삭제", description = "게시물 작성자가 자신의 게시물을 삭제합니다. (소프트 딜리트)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 게시물"),
            @ApiResponse(responseCode = "403", description = "게시물 삭제 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<String> deletePost(
            @PathVariable int postId,
            @Parameter(hidden = true) @LoginMember int memberId) {
        return studyPostService.deletePost(postId, memberId);
    }

}
