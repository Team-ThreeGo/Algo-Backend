package com.threego.algo.algorithm.command.application.service;

import com.threego.algo.algorithm.command.application.dto.*;
import com.threego.algo.algorithm.command.domain.aggregate.*;
import com.threego.algo.algorithm.command.domain.repository.*;
import com.threego.algo.common.service.S3Service;
import com.threego.algo.algorithm.query.service.AlgoQueryService;
import com.threego.algo.likes.command.application.service.LikesCommandService;
import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.likes.query.service.LikesQueryService;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlgoCommandServiceImpl implements AlgoCommandService {
    private final AlgoRoadmapCommandRepository algoRoadmapCommandRepository;
    private final AlgoPostCommandRepository algoPostCommandRepository;
    private final AlgoPostImageCommandRepository algoPostImageCommandRepository;
    private final AlgoQuizQuestionCommandRepository algoQuizQuestionCommandRepository;
    private final AlgoQuizOptionCommandRepository algoQuizOptionCommandRepository;
    private final AlgoCommentRepository algoCommentRepository;
    private final MemberAlgoCorrectQuizHistoryCommandRepository memberAlgoCorrectQuizHistoryCommandRepository;

    private final MemberCommandRepository memberCommandRepository;

    private final AlgoQueryService algoQueryService;
    private final LikesCommandService likesCommandService;
    private final LikesQueryService likesQueryService;

    private final S3Service s3Service;

    @Override
    public AlgoRoadmap createAlgoRoadmap(final AlgoRoadmapRequestDTO request) {
        validAlgoRoadmapTitle(request.getTitle());

        final AlgoRoadmap algoRoadmap = new AlgoRoadmap(request.getTitle(), request.getDescription(),
                request.getOrder() == null ? 1 : request.getOrder());

        return algoRoadmapCommandRepository.save(algoRoadmap);
    }

    @Transactional
    @Override
    public AlgoRoadmap updateAlgoRoadmap(final int roadmapId, final AlgoRoadmapRequestDTO request) throws Exception {
        // TODO 커스텀 예외 발생 및 처리 필요
        final AlgoRoadmap algoRoadmap = findAlgoRoadmapById(roadmapId);

        if (!algoRoadmap.getTitle().equals(request.getTitle())) {
            validAlgoRoadmapTitle(request.getTitle());
        }

        if (!algoRoadmap.getTitle().equals(request.getTitle())
                || !algoRoadmap.getDescription().equals(request.getDescription())
                || algoRoadmap.getOrder() != request.getOrder()) {
            algoRoadmap.updateAlgoRoadmap(request.getTitle(), request.getDescription(),
                    request.getOrder() == null ? 1 : request.getOrder());
        }

        return algoRoadmap;
    }

    @Transactional
    @Override
    public AlgoPostDetailResponseDTO createAlgoPost(final int memberId, final int roadmapId,
                                                    final AlgoPostRequestDTO request) {
        final Member member = findMemberById(memberId);
        final AlgoRoadmap algoRoadmap = findAlgoRoadmapById(roadmapId);

        AlgoPost algoPost = new AlgoPost(request.getTitle(), request.getContent(), algoRoadmap, member);
        algoPost = algoPostCommandRepository.save(algoPost);

        final AlgoPostDetailResponseDTO response = AlgoPostDetailResponseDTO.of(algoPost);

        // 이미지 처리 로직 수정
        List<String> savedImageUrls = new ArrayList<>();

        // 1. MultipartFile로 받은 이미지 처리 (새로 추가)
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (MultipartFile image : request.getImages()) {
                if (!image.isEmpty()) {
                    // 이미지 파일 유효성 검사
                    s3Service.validateImageFile(image);

                    // S3에 업로드
                    String imageUrl = s3Service.uploadFile(image, "algo-posts");

                    // DB에 저장
                    AlgoPostImage postImage = new AlgoPostImage(imageUrl, algoPost);
                    algoPostImageCommandRepository.save(postImage);

                    savedImageUrls.add(imageUrl);
                }
            }
        }

        // 2. 기존 imageUrls로 받은 URL 처리 (하위 호환성 유지)
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<AlgoPostImage> urlImages = saveAlgoPostImages(request.getImageUrls(), algoPost);
            savedImageUrls.addAll(urlImages.stream()
                    .map(AlgoPostImage::getImageUrl)
                    .collect(Collectors.toList()));
        }

        response.setImageUrls(savedImageUrls);
        return response;
    }

    @Transactional
    @Override
    public void deleteAlgoPost(final int postId) {
        final AlgoPost algoPost = findAlgoPostById(postId);

        if (algoPost.getVisibility().equals("N")) {
            throw new RuntimeException("이미 삭제 처리된 알고리즘 학습 게시물입니다.");
        }

        // TODO 주석 제거 필요
        // 삭제할 게시물에 속한 문제 수 카운팅
        final int postQuizCount = algoQuizQuestionCommandRepository.countByAlgoPost(algoPost);

        // 삭제할 게시물이 속한 로드맵의 문제 수 가져오기
        final int totalQuizCount = algoPost.getAlgoRoadmap().getQuestionCount();

        // 게시물 상태 변경 (Y -> N)
        algoPost.updateVisibility();

        // 로드맵의 문제 수 갱신
        algoPost.getAlgoRoadmap().updateQuestionCount(totalQuizCount - postQuizCount);
    }

    @Transactional
    @Override
    public void createComment(final int memberId, final int postId, final AlgoCommentRequestDTO request) throws Exception {
        final Member member = findMemberById(memberId);

        final AlgoPost algoPost = findAlgoPostById(postId);

        AlgoComment parent = null;

        if (request.getParentId() != null) {
            parent = findAlgoCommentId(request.getParentId());
        }

        final AlgoComment comment = new AlgoComment(request.getContent(), member, algoPost, parent);

        algoCommentRepository.save(comment);

        algoPost.updateCommentCount(algoPost.getCommentCount() + 1);
    }

    @Transactional
    @Override
    public void updateComment(final int memberId, final int commentId, final AlgoCommentRequestDTO request) throws Exception {
        final AlgoComment comment = findAlgoCommentId(commentId);

        final Member member = findMemberById(memberId);

        validAuthor(comment.getMember(), member);

        comment.updateContent(request.getContent());
    }

    @Transactional
    @Override
    public void deleteComment(final int memberId, final int commentId) throws Exception {
        final AlgoComment comment = findAlgoCommentId(commentId);

        final Member member = findMemberById(memberId);

        validAuthor(comment.getMember(), member);

        if (comment.getVisibility().equals("N")) {
            throw new RuntimeException("이미 삭제 처리된 댓글입니다.");
        }

        comment.updateVisibility();

        final AlgoPost algoPost = comment.getAlgoPost();

        algoPost.updateCommentCount(algoPost.getCommentCount() - 1);
    }

    @Transactional
    @Override
    public void createCorrectQuizHistory(int memberId, int questionId) throws Exception {
        final Member member = findMemberById(memberId);

        final AlgoQuizQuestion quizQuestion = findAlgoQuizQuestion(questionId);

        final MemberAlgoCorrectQuizHistoryId id = new MemberAlgoCorrectQuizHistoryId(member, quizQuestion);

        if (!memberAlgoCorrectQuizHistoryCommandRepository.existsById(id)) {
            memberAlgoCorrectQuizHistoryCommandRepository.save(new MemberAlgoCorrectQuizHistory(id));
        } else {
            throw new RuntimeException("이미 등록된 퀴즈 정답 제출 이력입니다.");
        }

        final int correctQuizCount = algoQueryService.countMemberCorrectAnswersInRoadmap(memberId,
                quizQuestion.getAlgoPost().getAlgoRoadmap().getId());

        if (correctQuizCount + 1 == quizQuestion.getAlgoPost().getAlgoRoadmap().getQuestionCount()) {
            // TODO 로드맵 대분류의 퀴즈를 전부 맞힌 경우 포인트 20 증가
            log.info("{}번 회원이 로드맵 대분류 {}번의 퀴즈를 모두 달성했으므로 20 포인트가 지급되었습니다."
                    , memberId, quizQuestion.getAlgoPost().getAlgoRoadmap().getId());
        }
    }

    @Transactional
    @Override
    public AlgoQuizQuestionResponseDTO createAlgoQuiz(int postId, AlgoQuizQuestionRequestDTO request) {
        final AlgoPost algoPost = findAlgoPostById(postId);

        validQuizQuestion(request.getQuestion());

        final AlgoQuizQuestion algoQuizQuestion = new AlgoQuizQuestion(request.getQuestion(), request.getType(), algoPost);

        final AlgoQuizQuestion savedAlgoQuizQuestion = algoQuizQuestionCommandRepository.save(algoQuizQuestion);

        final List<AlgoQuizOption> options = request.getOptions().stream()
                .map((option) -> new AlgoQuizOption(option.getOptionText(), option.isCorrect(), savedAlgoQuizQuestion))
                .collect(Collectors.toList());

        final List<AlgoQuizOptionResponseDTO> optionResponse = algoQuizOptionCommandRepository.saveAll(options).stream()
                .map(AlgoQuizOptionResponseDTO::of)
                .collect(Collectors.toList());

        algoPost.getAlgoRoadmap().updateQuestionCount(algoPost.getAlgoRoadmap().getQuestionCount() + 1);

        return AlgoQuizQuestionResponseDTO.of(savedAlgoQuizQuestion, optionResponse);
    }

    @Transactional
    @Override
    public AlgoQuizQuestionResponseDTO updateAlgoQuiz(final int quizQuestionId,
                                                      final UpdateAlgoQuizQuestionRequestDTO request) throws Exception {
        final AlgoQuizQuestion quizQuestion = findAlgoQuizQuestion(quizQuestionId);

        if (!quizQuestion.getQuestion().equals(request.getQuestion())) {
            validQuizQuestion(request.getQuestion());
            quizQuestion.updateQuestion(request.getQuestion());
        }

        final List<AlgoQuizOption> algoQuizOptions = algoQuizOptionCommandRepository.findByAlgoQuizQuestion(quizQuestion);

        final List<AlgoQuizOptionResponseDTO> optionResponse = new ArrayList<>();

        for (UpdateAlgoQuizOptionRequestDTO dto : request.getOptions()) {
            final AlgoQuizOption algoQuizOption = algoQuizOptions.stream()
                    .filter((option) -> option.getId() == dto.getOptionId())
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("알고리즘 학습 게시물 퀴즈의 보기(ID: " + dto.getOptionId() + ") 을(를) 찾을 수 없습니다."));

            algoQuizOption.updateAlgoQuizOption(dto.getOptionText(), dto.isCorrect());

            optionResponse.add(AlgoQuizOptionResponseDTO.of(algoQuizOption));
        }

        return AlgoQuizQuestionResponseDTO.of(quizQuestion, optionResponse);
    }

    @Transactional
    @Override
    public AlgoPostDetailResponseDTO updateAlgoPost(final int postId, final AlgoPostRequestDTO request) {
        final AlgoPost algoPost = findAlgoPostById(postId);

        algoPost.updateAlgoPost(request.getTitle(), request.getContent());

        final List<AlgoPostImage> originAlgoPostImage = algoPostImageCommandRepository.findByAlgoPost(algoPost);

        final List<String> originAlgoPostImageUrls = originAlgoPostImage.stream()
                .map(AlgoPostImage::getImageUrl)
                .collect(Collectors.toList());

        final List<String> addAlgoPostImages = request.getImageUrls().stream()
                .filter((image) -> !originAlgoPostImageUrls.contains(image))
                .collect(Collectors.toList());

        final List<AlgoPostImage> removeAlgoPostImages = originAlgoPostImage.stream()
                .filter((image) -> !request.getImageUrls().contains(image.getImageUrl()))
                .collect(Collectors.toList());

        algoPostImageCommandRepository.deleteAll(removeAlgoPostImages);

        saveAlgoPostImages(addAlgoPostImages, algoPost);

        final AlgoPostDetailResponseDTO response = AlgoPostDetailResponseDTO.of(algoPost);

        final List<String> algoPostImages = algoPostImageCommandRepository.findByAlgoPost(algoPost).stream()
                .map(AlgoPostImage::getImageUrl)
                .collect(Collectors.toList());

        response.setImageUrls(algoPostImages);

        return response;
    }

    @Transactional
    @Override
    public void deleteCommentForAdmin(final int commentId) {
        final AlgoComment comment = findAlgoCommentId(commentId);

        if (comment.getVisibility().equals("N")) {
            throw new RuntimeException("이미 삭제 처리된 댓글입니다.");
        }

        comment.updateVisibility();

        final AlgoPost algoPost = comment.getAlgoPost();

        algoPost.updateCommentCount(algoPost.getCommentCount() - 1);
    }

    @Transactional
    @Override
    public void deleteAlgoQuizQuestion(final int quizQuestionId) throws Exception {
        final AlgoQuizQuestion algoQuizQuestion = findAlgoQuizQuestion(quizQuestionId);

        final AlgoPost algoPost = algoQuizQuestion.getAlgoPost();

        algoQuizOptionCommandRepository.deleteByAlgoQuizQuestion(algoQuizQuestion);

        algoQuizQuestionCommandRepository.deleteById(algoQuizQuestion.getId());

        final int totalQuizCount = algoPost.getAlgoRoadmap().getQuestionCount();

        algoPost.getAlgoRoadmap().updateQuestionCount(totalQuizCount - 1);
    }

    @Transactional
    @Override
    public void createAlgoPostLikes(final int memberId, final int postId) {
        final Member member = findMemberById(memberId);
        final AlgoPost algoPost = findAlgoPostById(postId);

        if (member == algoPost.getMember()) {
            throw new RuntimeException("자신이 작성한 글은 추천할 수 없습니다.");
        }

        if (likesQueryService.existsLikesByMemberIdAndPostIdAndPostType(memberId, postId, Type.ALGO_POST)) {
            throw new RuntimeException("이미 추천한 게시물입니다.");
        }

        likesCommandService.createLikes(member, algoPost, Type.ALGO_POST);

        algoPost.increaseLikeCount();
    }

    private void validQuizQuestion(final String question) {
        if (algoQuizQuestionCommandRepository.existsByQuestionLike(question)) {
            throw new RuntimeException("이미 동일한 내용의 퀴즈 질문이 존재합니다.");
        }
    }

    private AlgoQuizQuestion findAlgoQuizQuestion(final int questionId) throws Exception {
        return algoQuizQuestionCommandRepository.findById(questionId).orElseThrow(Exception::new);
    }

    private void validAuthor(final Member author, final Member loginMember) {
        if (author != loginMember) {
            // TODO 커스텀 예외 발생
            throw new RuntimeException("작성자가 아니므로 수정 및 삭제 권한이 없습니다.");
        }
    }

    private AlgoComment findAlgoCommentId(final int parentId) {
        return algoCommentRepository.findById(parentId).orElseThrow(() -> {
            throw new RuntimeException("알고리즘 학습 게시물 댓글(ID: " + parentId + ") 을(를) 찾을 수 없습니다.");
        });
    }

    private List<AlgoPostImage> saveAlgoPostImages(final List<String> imageUrls, final AlgoPost algoPost) {
        final List<AlgoPostImage> algoPostImages = imageUrls.stream()
                .map((imageUrl) -> new AlgoPostImage(imageUrl, algoPost))
                .collect(Collectors.toList());

        return algoPostImageCommandRepository.saveAll(algoPostImages);
    }

    private void validAlgoRoadmapTitle(final String title) {
        if (algoRoadmapCommandRepository.existsByTitle(title)) {
            throw new RuntimeException("이미 존재하는 로드맵 제목입니다.");
        }
    }

    private AlgoRoadmap findAlgoRoadmapById(final int roadmapId) {
        return algoRoadmapCommandRepository.findById(roadmapId).orElseThrow(() -> {
            throw new RuntimeException("알고리즘 학습 로드맵 대분류(ID: " + roadmapId + ") 을(를) 찾을 수 없습니다.");
        });
    }

    private Member findMemberById(final int memberId) {
        return memberCommandRepository.findById(memberId).orElseThrow(() -> {
            throw new RuntimeException("회원(ID: " + memberId + ") 을(를) 찾을 수 없습니다.");
        });
    }

    private AlgoPost findAlgoPostById(final int postId) {
        return algoPostCommandRepository.findById(postId).orElseThrow(() -> {
            throw new RuntimeException("알고리즘 학습 게시물(ID: " + postId + ") 을(를) 찾을 수 없습니다.");
        });
    }
}