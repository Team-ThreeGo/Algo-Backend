package com.threego.algo.career.command.application.controller;

import com.threego.algo.career.command.application.dto.CareerCommentRequest;
import com.threego.algo.career.command.application.dto.CareerPostCreateRequest;
import com.threego.algo.career.command.application.service.CareerCommandService;
import com.threego.algo.common.auth.LoginMember;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "Career Info - Member Command",
        description = "회원용 기업별 정보 공유 API (Command)"
)
@RestController
@RequestMapping("/career-info")
public class CareerCommandController {
    private final CareerCommandService service;

    @Autowired
    public CareerCommandController(@Qualifier("careerCommandServiceImpl") CareerCommandService service) {
        this.service = service;
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 등록",
            description = "회원이 기업별 정보 공유 게시판에 게시물을 등록합니다."
    )
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> createPost(
            @Parameter(hidden = true) @LoginMember int memberId,
            @Parameter(description = "제목") @RequestParam String title,
            @Parameter(description = "내용") @RequestParam String content,
            @Parameter(description = "이미지 파일 (선택, 최대 5MB)")
            @RequestPart(value = "image", required = false) MultipartFile image) {

        CareerPostCreateRequest request = new CareerPostCreateRequest();
        request.setTitle(title);
        request.setContent(content);
        request.setImage(image);

        Integer postId = service.createPost(request, memberId);
        return ResponseEntity.ok(postId);
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 삭제 (회원)",
            description = "회원이 자신의 게시물을 삭제합니다. soft delete로 visibility를 'N'으로 변경합니다."
    )
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @Parameter(hidden = true) @LoginMember int memberId,
            @PathVariable Integer postId
    ) {
        service.deletePost(postId, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 게시물에 댓글 등록",
            description = "회원이 게시물에 댓글 또는 대댓글을 등록합니다."
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Integer> createComment(
            @Parameter(hidden = true) @LoginMember int memberId,
            @PathVariable Integer postId,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestBody CareerCommentRequest request
    ) {
        Integer commentId = service.createComment(postId, parentId, request, memberId);
        return ResponseEntity.ok(commentId);
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 수정",
            description = "회원이 자신의 댓글을 수정합니다."
    )
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
            @Parameter(hidden = true) @LoginMember int memberId,
            @PathVariable Integer commentId,
            @RequestBody CareerCommentRequest request
    ) {
        service.updateComment(commentId, request, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 삭제 (회원)",
            description = "회원이 자신의 댓글을 삭제합니다. soft delete로 visibility='N'으로 변경합니다."
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(hidden = true) @LoginMember int memberId,
            @PathVariable Integer commentId
    ) {
        service.deleteComment(commentId, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 추천 (회원)",
            description = "회원이 자신이 작성하지 않는 게시물을 추천합니다. 추천 시 게시물의 작성자의 포인트가 1씩 증가합니다."
    )
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<Void> createCareerPostLikes(
            @Parameter(hidden = true) @LoginMember int memberId,
            @PathVariable("postId") final int postId
    ) {
        service.createCareerPostLikes(memberId, postId);

        return ResponseEntity.ok().build();
    }
}
