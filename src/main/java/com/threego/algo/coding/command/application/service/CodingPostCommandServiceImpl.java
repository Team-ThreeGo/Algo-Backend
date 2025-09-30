package com.threego.algo.coding.command.application.service;

import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostImageRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.domain.aggregate.CodingComment;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.coding.command.domain.aggregate.CodingPostImage;
import com.threego.algo.coding.command.domain.aggregate.CodingProblem;
import com.threego.algo.coding.command.domain.repository.CodingCommentRepository;
import com.threego.algo.coding.command.domain.repository.CodingPostImageRepository;
import com.threego.algo.coding.command.domain.repository.CodingPostRepository;
import com.threego.algo.coding.command.domain.repository.CodingProblemRepository;
import com.threego.algo.likes.command.application.service.LikesCommandService;
import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.likes.query.service.LikesQueryService;
import com.threego.algo.common.service.S3Service;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CodingPostCommandServiceImpl implements CodingPostCommandService {

    private final CodingPostRepository postRepository;
    private final CodingPostImageRepository imageRepository;
    private final CodingCommentRepository commentRepository;
    private final CodingProblemRepository problemRepository;
    private final MemberCommandRepository memberRepository;
    private final S3Service s3Service;
    private final CodingPostImageRepository codingPostImageRepository;

    private final LikesCommandService likesCommandService;
    private final LikesQueryService likesQueryService;

    @Override
    @Transactional
    public int createPost(CodingPostRequestDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("작성자(Member) 없음: " + dto.getMemberId()));

        CodingProblem problem = problemRepository.findById(dto.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("문제 없음: " + dto.getProblemId()));

        CodingPost post = CodingPost.create(member, problem, dto.getTitle(), dto.getContent());
        CodingPost saved = postRepository.save(post);

        // 이미지 업로드 및 저장
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            for (MultipartFile image : dto.getImages()) {
                if (!image.isEmpty()) {
                    // 이미지 파일 유효성 검사
                    s3Service.validateImageFile(image);

                    // S3에 업로드
                    String imageUrl = s3Service.uploadFile(image, "coding-posts");

                    // DB에 저장
                    CodingPostImage postImage = new CodingPostImage(saved, imageUrl);
                    codingPostImageRepository.save(postImage);
                }
            }
        }

        // 즉시 동기화: 해당 문제의 postCount 재계산
        problem.syncPostCount();

        return saved.getId();
    }

    @Override
    @Transactional
    public int addImage(int postId, CodingPostImageRequestDTO dto) {
        CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음: " + postId));

        CodingPostImage image = new CodingPostImage(post, dto.getImageUrl());
        CodingPostImage saved = imageRepository.save(image);
        return saved.getId();
    }

    @Override
    @Transactional
    public CodingPost updatePost(int postId, CodingPostRequestDTO dto) {
        CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음: " + postId));

        if ("N".equals(post.getVisibility())) {
            throw new IllegalStateException("삭제된 게시물은 수정할 수 없습니다.");
        }

        post.update(dto.getTitle(), dto.getContent());
        return post;
    }

    @Override
    @Transactional
    public void softDeletePost(int postId) {
        CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음: " + postId));

        if ("N".equals(post.getVisibility())) {
            throw new IllegalStateException("이미 삭제된 게시물입니다.");
        }

        post.delete(); // visibility = 'N'

        // 즉시 동기화: 해당 문제의 postCount 재계산
        CodingProblem problem = post.getProblem();
        if (problem != null) {
            problem.syncPostCount();
        }
    }

    @Override
    @Transactional
    public int addComment(int postId,
                          Integer parentId,
                          CodingCommentRequestDTO dto) {
        // member, post 조회
        Member member = memberRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("작성자(Member) 없음: "));

        CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음: " + postId));

        // parent가 있으면 검증: 부모가 같은 게시물에 속해야 함
        CodingComment parent = null;
        if (parentId != null) {
            parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글 없음: "));
            if (parent.getPost().getId() != postId) {
                throw new IllegalArgumentException("부모 댓글은 같은 게시물에 속해야 합니다.");
            }
            if ("N".equals(parent.getVisibility())) {
                throw new IllegalStateException("부모 댓글이 이미 삭제되어 있습니다.");
            }
        }

        CodingComment comment = CodingComment.create(member, post, dto.getContent(), parent);
        CodingComment saved = commentRepository.save(comment);

        // 게시물의 댓글 수 증가 (대댓글도 게시물 전체 commentCount에 포함)
        post.increaseCommentCount();

        return saved.getId();
    }

    @Override
    @Transactional
    public void updateComment(int commentId, CodingCommentRequestDTO dto) {
        CodingComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음: " + commentId));

        if ("N".equals(comment.getVisibility())) {
            throw new IllegalStateException("삭제된 댓글은 수정할 수 없습니다.");
        }

        comment.update(dto.getContent());
    }

    @Override
    @Transactional
    public void softDeleteComment(int commentId) {
        CodingComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음: " + commentId));

        if ("N".equals(comment.getVisibility())) {
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }

        comment.delete(); // visibility = 'N'

        // 게시물의 댓글 수 감소 (대댓글 삭제도 게시물 전체 count에 반영)
        CodingPost post = comment.getPost();
        if (post != null) {
            post.decreaseCommentCount();
        }
    }

    @Transactional
    @Override
    public void createCodingPostLikes(final int memberId, final int postId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(memberId + "번 회원이 존재하지 않습니다."));

        final CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(memberId + "번 코딩 문제 풀이 게시물이 존재하지 않습니다."));

        if (member == post.getMemberId()) {
            throw new RuntimeException("자신이 작성한 글은 추천할 수 없습니다.");
        }

        if (likesQueryService.existsLikesByMemberIdAndPostIdAndPostType(memberId, postId, Type.CODING_POST)) {
            throw new RuntimeException("이미 추천한 게시물입니다.");
        }

        likesCommandService.createLikes(member, post, Type.CODING_POST);

        post.getMemberId().increasePoint(1);

        post.increaseLikeCount();
    }
}