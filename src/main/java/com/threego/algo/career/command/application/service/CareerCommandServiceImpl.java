package com.threego.algo.career.command.application.service;

import com.threego.algo.career.command.application.dto.CareerCommentRequest;
import com.threego.algo.career.command.application.dto.CareerPostCreateRequest;
import com.threego.algo.career.command.domain.aggregate.CareerInfoComment;
import com.threego.algo.career.command.domain.aggregate.CareerInfoPost;
import com.threego.algo.career.command.domain.repository.CareerCommentRepository;
import com.threego.algo.career.command.domain.repository.CareerPostRepository;
import com.threego.algo.likes.command.application.service.LikesCommandService;
import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.likes.query.service.LikesQueryService;
import com.threego.algo.common.service.S3Service;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CareerCommandServiceImpl implements CareerCommandService {
    private final CareerPostRepository careerPostRepository;
    private final CareerCommentRepository careerCommentRepository;
    private final MemberCommandRepository memberRepository;
    private final S3Service s3Service;

    private final LikesCommandService likesCommandService;
    private final LikesQueryService likesQueryService;

    @Autowired
    public CareerCommandServiceImpl(CareerPostRepository careerPostRepository
            , CareerCommentRepository careerCommentRepository
            , MemberCommandRepository memberRepository
            , S3Service s3Service                        
            , LikesCommandService likesCommandService
            , LikesQueryService likesQueryService) {
        this.careerPostRepository = careerPostRepository;
        this.careerCommentRepository = careerCommentRepository;
        this.memberRepository = memberRepository;
        this.s3Service = s3Service;
        this.likesCommandService = likesCommandService;
        this.likesQueryService = likesQueryService;
    }

    @Transactional
    @Override
    public Integer createPost(CareerPostCreateRequest request) {
        // TODO: 로그인 회원 정보 가져오기 (Spring Security에서 인증 객체 활용)
        Member member = memberRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("테스트용 회원이 없습니다."));

        String imageUrl = null;

        // 이미지 파일이 있으면 S3에 업로드
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            s3Service.validateImageFile(request.getImage());
            imageUrl = s3Service.uploadFile(request.getImage(), "career-posts");
        }

        CareerInfoPost post = CareerInfoPost.create(
                member,
                request.getTitle(),
                request.getContent(),
                imageUrl
        );

        return careerPostRepository.save(post).getId();
    }

    @Transactional
    @Override
    public void deletePost(int postId) {
        CareerInfoPost post = careerPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        // TODO: 로그인한 회원 == post.getMember() 확인 (본인 글만 삭제 가능)

        if ("N".equals(post.getVisibility())) {
            throw new IllegalStateException("이미 삭제된 게시물입니다.");
        }

        post.delete();
    }

    @Transactional
    @Override
    public Integer createComment(int postId,
                                 Integer parentId,
                                 CareerCommentRequest request
    ) {
        CareerInfoPost post = careerPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        // TODO: 로그인 회원 정보 가져오기 (Spring Security에서 인증 객체 활용)
        Member member = memberRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("테스트용 회원이 없습니다."));

        CareerInfoComment parent = null;
        if (parentId != null) {
            parent = careerCommentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        CareerInfoComment comment = new CareerInfoComment(
                parent,
                post,
                member,
                request.getContent()
        );
        careerCommentRepository.save(comment);
        post.increaseCommentCount();

        return comment.getId();
    }

    @Transactional
    @Override
    public void updateComment(int commentId, CareerCommentRequest request) {
        CareerInfoComment comment = careerCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // TODO: 로그인 회원 == 작성자 확인
        comment.updateComment(request.getContent());
    }

    @Transactional
    @Override
    public void deleteComment(int commentId) {
        CareerInfoComment comment = careerCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // TODO: 로그인 회원 == 작성자 확인

        if ("N".equals(comment.getVisibility())) {
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }

        comment.deleteComment();
        comment.getPost().decreaseCommentCount();
    }

    @Transactional
    @Override
    public void createCareerPostLikes(final int memberId, final int postId) {
        // TODO: 로그인 회원 정보 가져오기 (Spring Security에서 인증 객체 활용)
        final Member member = memberRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("테스트용 회원이 없습니다."));

        final CareerInfoPost post = careerPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        if (member == post.getMember()) {
            throw new RuntimeException("자신이 작성한 글은 추천할 수 없습니다.");
        }

        if (likesQueryService.existsLikesByMemberIdAndPostIdAndPostType(memberId, postId, Type.CAREER_INFO_POST)) {
            throw new RuntimeException("이미 추천한 게시물입니다.");
        }

        likesCommandService.createLikes(member, post, Type.CAREER_INFO_POST);

        post.getMember().increasePoint(1);

        post.increaseLikeCount();
    }
}