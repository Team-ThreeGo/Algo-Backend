package com.threego.algo.study.command.application.controller;

import com.threego.algo.study.command.application.dto.create.StudyPostCreateDTO;
import com.threego.algo.study.command.application.dto.create.StudyPostCreateResponseDTO;
import com.threego.algo.study.command.application.dto.update.StudyPostUpdateDTO;
import com.threego.algo.study.command.application.service.StudyPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
    public ResponseEntity<StudyPostCreateResponseDTO> createPost(
            @PathVariable int studyId,

            @RequestPart(value = "post")
            @Parameter(description = "게시물 정보",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            StudyPostCreateDTO postDto,

            @RequestPart(value = "images", required = false) List<MultipartFile> images,

            @RequestParam int memberId) {

        return studyPostService.createPost(studyId, memberId, postDto, images);
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
            @RequestParam int memberId) {
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
            @RequestParam int memberId) {
        return studyPostService.deletePost(postId, memberId);
    }

}
