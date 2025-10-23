package com.threego.algo.coding.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostImageRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.application.service.CodingPostCommandService;
import com.threego.algo.common.auth.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "Coding - Member Command",
        description = "회원용 코딩 게시물 API (Command)"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/coding")
public class CodingPostCommandController {

    private final CodingPostCommandService codingPostCommandService;
    private final ObjectMapper objectMapper;
    private CodingPostRequestDTO data;

    @Operation(
            summary = "코딩풀이 게시물 등록 (회원)",
            description = "회원이 코딩풀이 게시물을 등록합니다."
    )
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> createPost(
            @Parameter(description = "작성자 ID") @LoginMember int memberId,
            @Parameter(description = "문제 ID") @RequestParam int problemId,
            @Parameter(description = "풀이 제목, 코드내용") @RequestPart("data") String dataJson,
            @Parameter(description = "이미지 파일")
            @RequestPart(value = "images", required = false) MultipartFile images
    ) throws Exception {

        // JSON 문자열 → DTO 변환
        CodingPostRequestDTO dto = objectMapper.readValue(dataJson, CodingPostRequestDTO.class);

        // MultipartFile 이미지 설정
        dto.setImages(images);

        // Service 호출
        int id = codingPostCommandService.createPost(memberId, problemId, dto);

        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "코딩풀이 게시물 수정 (회원)",
            description = "회원이 코딩풀이 게시물을 수정합니다."
    )
    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody CodingPostRequestDTO request,
                                             @LoginMember int memberId) throws Exception{
        codingPostCommandService.updatePost(memberId, postId, request);
        return ResponseEntity.ok("수정 완료");
    }

    @Operation(
            summary = "코딩풀이 게시물 삭제 (회원)",
            description = "회원이 코딩풀이 게시물을 삭제(soft-delete)합니다."
    )
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId,
                                             @LoginMember int memberId) throws Exception {
        codingPostCommandService.softDeletePost(memberId, postId);
        return ResponseEntity.ok("삭제 완료");
    }

    @Operation(
            summary = "코딩풀이 게시물 이미지 등록 (회원)",
            description = "회원이 코딩풀이 게시물을 등록합니다."
    )
    @PostMapping("/posts/{postId}/images")
    public ResponseEntity<Integer> addImage(@PathVariable int postId, @RequestPart("image") MultipartFile image) {
        CodingPostImageRequestDTO dto = new CodingPostImageRequestDTO(image);
        int id = codingPostCommandService.addImage(postId, dto);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "코딩풀이 게시물 댓글 등록 (회원)",
            description = "회원이 코딩풀이 게시물의 댓글을 등록합니다.(parentId가 있으면 대댓글 등록)"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Integer> createComment(
            @PathVariable int postId,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestBody CodingCommentRequestDTO request,
            @LoginMember @Parameter(description = "작성자 ID") int memberId) throws Exception {
        int commentid = codingPostCommandService.addComment(memberId, postId, parentId, request);
        return ResponseEntity.ok(commentid);
    }

    @Operation(
            summary = "코딩풀이 게시물 댓글 수정 (회원)",
            description = "회원이 코딩풀이 게시물의 댓글을 수정합니다."
    )
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable int commentId, @RequestBody CodingCommentRequestDTO request,
                                                @LoginMember @Parameter(description = "작성자 ID") int memberId) throws Exception{
        codingPostCommandService.updateComment(memberId, commentId, request);
        return ResponseEntity.ok("수정 완료");
    }

    @Operation(
            summary = "코딩풀이 게시물 댓글 삭제 (회원)",
            description = "회원이 코딩풀이 게시물의 댓글을 삭제합니다."
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId,
                                                @LoginMember @Parameter(description = "작성자 ID") int memberId) throws Exception{
        codingPostCommandService.softDeleteComment(memberId, commentId);
        return ResponseEntity.ok("삭제 완료");
    }

    @Operation(summary = "코딩 문제 풀이 게시물 추천",
            description = "회원이 코딩 문제 풀이 게시물을 추천하는 API입니다.")
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<Void> createdCodingPostLikes(
            @PathVariable("postId") int postId,
            @LoginMember @Parameter(description = "작성자 ID") int memberId) {
        codingPostCommandService.createCodingPostLikes(memberId, postId);

        return ResponseEntity.ok().build();
    }
}