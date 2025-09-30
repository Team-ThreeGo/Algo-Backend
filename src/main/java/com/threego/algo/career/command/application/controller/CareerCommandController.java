package com.threego.algo.career.command.application.controller;

import com.threego.algo.career.command.application.dto.CareerCommentRequest;
import com.threego.algo.career.command.application.dto.CareerPostCreateRequest;
import com.threego.algo.career.command.application.service.CareerCommandService;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import com.threego.algo.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final JwtUtil jwtUtil;
    private final MemberCommandRepository memberCommandRepository;

    @Autowired
    public CareerCommandController(CareerCommandService service, JwtUtil jwtUtil, MemberCommandRepository memberCommandRepository) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.memberCommandRepository = memberCommandRepository;
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 등록",
            description = "회원이 기업별 정보 공유 게시판에 게시물을 등록합니다."
    )
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> createPost(
            HttpServletRequest request,
            @Parameter(description = "제목") @RequestParam String title,
            @Parameter(description = "내용") @RequestParam String content,
            @Parameter(description = "이미지 파일 (선택, 최대 5MB)")
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        // 🔑 JWT 토큰에서 회원 정보 추출
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.parseClaims(token);
        String email = claims.getSubject();

        Member member = memberCommandRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        int memberId = member.getId();

        // DTO 생성
        CareerPostCreateRequest dto = new CareerPostCreateRequest();
        dto.setMemberId(memberId);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setImage(image);

        Integer postId = service.createPost(dto);
        return ResponseEntity.ok(postId);
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 삭제 (회원)",
            description = "회원이 자신의 게시물을 삭제합니다. soft delete로 visibility를 'N'으로 변경합니다."
    )
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        service.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 게시물에 댓글 등록",
            description = "회원이 게시물에 댓글 또는 대댓글을 등록합니다."
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Integer> createComment(
            @PathVariable Integer postId,
            @RequestParam(value = "parentId", required = false) Integer parentId,
            @RequestBody CareerCommentRequest request
    ) {
        Integer commentId = service.createComment(postId, parentId, request);
        return ResponseEntity.ok(commentId);
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 수정",
            description = "회원이 자신의 댓글을 수정합니다."
    )
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Integer commentId,
            @RequestBody CareerCommentRequest request
    ) {
        service.updateComment(commentId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 댓글 삭제 (회원)",
            description = "회원이 자신의 댓글을 삭제합니다. soft delete로 visibility='N'으로 변경합니다."
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        service.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "기업별 정보 공유 게시물 추천 (회원)",
            description = "회원이 자신이 작성하지 않는 게시물을 추천합니다. 추천 시 게시물의 작성자의 포인트가 1씩 증가합니다."
    )
    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<Void> createCareerPostLikes(@PathVariable("postId") final int postId) {
        // TODO. memberID는 Authentication에서 받아오도록 수정 필요
        service.createCareerPostLikes(1, postId);

        return ResponseEntity.ok().build();
    }
}
