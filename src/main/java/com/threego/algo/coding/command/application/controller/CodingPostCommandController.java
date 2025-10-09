package com.threego.algo.coding.command.application.controller;

import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostImageRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.application.service.CodingPostCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(
        name = "Coding - Member Command",
        description = "회원용 코딩 게시물 API (Command)"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/coding")
public class CodingPostCommandController {

    private final CodingPostCommandService codingPostCommandService;

    // 게시물 등록
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> createPost(
            @Parameter(description = "작성자 ID") @RequestParam Integer memberId,
            @Parameter(description = "문제 ID") @RequestParam Integer problemId,
            @Parameter(description = "게시물 본문 JSON") @RequestPart("data") CodingPostRequestDTO data,
//            @Parameter(description = "제목") @RequestParam String title,
//            @Parameter(description = "내용") @RequestParam String content,
            @Parameter(description = "이미지 파일")
            @RequestPart(value = "images", required = false) MultipartFile images) {

//        CodingPostRequestDTO request = new CodingPostRequestDTO();
//        request.setMemberId(memberId);
//        request.setProblemId(problemId);
//        request.setTitle(title);
//        request.setContent(content);
//        request.setImages(images);

        data.setMemberId(memberId);
        data.setProblemId(problemId);
        data.setImages(images);

        int id = codingPostCommandService.createPost(data);
        return ResponseEntity.ok(id);
    }

    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody CodingPostRequestDTO request) {
        codingPostCommandService.updatePost(postId, request);
        return ResponseEntity.ok("수정 완료");
    }

    // 게시물 삭제 (회원용 soft-delete)
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        codingPostCommandService.softDeletePost(postId);
        return ResponseEntity.ok("삭제 완료");
    }

    // 게시물 이미지 등록
//    @PostMapping("/posts/{postId}/images")
//    public ResponseEntity<Integer> addImage(@PathVariable int postId, @RequestBody CodingPostImageRequestDTO request) {
//        int id = codingPostCommandService.addImage(postId, request);
//        return ResponseEntity.ok(id);
//    }

    // 댓글 등록 (parentId가 있으면 대댓글)
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Integer> createComment(
            @PathVariable int postId,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestBody CodingCommentRequestDTO request) {
        Integer commentid = codingPostCommandService.addComment(postId, parentId, request);
        return ResponseEntity.ok(commentid);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable int commentId, @RequestBody CodingCommentRequestDTO request) {
        codingPostCommandService.updateComment(commentId, request);
        return ResponseEntity.ok("수정 완료");
    }

    // 댓글 삭제 (회원용 soft-delete)
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
        codingPostCommandService.softDeleteComment(commentId);
        return ResponseEntity.ok("삭제 완료");
    }

    @Operation(summary = "코딩 문제 풀이 게시물 추천",
            description = "회원이 코딩 문제 풀이 게시물을 추천하는 API입니다.")
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<Void> createdCodingPostLikes(@PathVariable("postId") final int postId) {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        codingPostCommandService.createCodingPostLikes(1, postId);

        return ResponseEntity.ok().build();
    }
}