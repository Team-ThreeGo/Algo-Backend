package com.threego.algo.coding.command.application.service;

import com.threego.algo.coding.command.application.dto.CodingCommentRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingPostRequestDTO;
import com.threego.algo.coding.command.application.dto.CodingProblemRequestDTO;
import com.threego.algo.coding.command.domain.aggregate.CodingComment;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.coding.command.domain.aggregate.CodingProblem;
import com.threego.algo.coding.command.domain.repository.CodingCommentRepository;
import com.threego.algo.coding.command.domain.repository.CodingPostRepository;
import com.threego.algo.coding.command.domain.repository.CodingProblemRepository;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCodingPostCommandServiceImpl implements AdminCodingPostCommandService {
    private final CodingPostRepository postRepository;
    private final CodingCommentRepository commentRepository;
    private final CodingProblemRepository problemRepository;
    private final MemberCommandRepository memberRepository;

    @Override
    @Transactional
    public CodingPost updatePost(int postId, CodingPostRequestDTO dto) {
        CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
        post.update(dto.getTitle(), dto.getContent());
        return post;
    }

    @Override
    @Transactional
    public void deletePost(int postId) {
        CodingPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
        post.delete();
    }

    @Override
    @Transactional
    public CodingComment updateComment(int commentId, CodingCommentRequestDTO dto) {
        CodingComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
        comment.update(dto.getContent());
        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(int commentId) {
        CodingComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
        comment.delete();
    }

    @Override
    @Transactional
    public CodingProblem createProblem(CodingProblemRequestDTO dto) {
//        Member admin = MemberCommandRepository.findById(adminMemberId)
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("관리자 계정을 찾을 수 없습니다."+ dto.getMemberId()));

        CodingProblem problem = CodingProblem.builder()
                .member(member)   // 여기서는 Member 객체
                .platform(dto.getPlatform())
                .title(dto.getProblemTitle())
                .problemUrl(dto.getProblemUrl())
                .difficulty(dto.getDifficulty())
                .content(dto.getContent())
                .input(dto.getInput())
                .output(dto.getOutput())
                .constraints(dto.getConstraints())
                .build();

        // 관리자 정보 세팅
//        problem.setMemberId(admin);
        return problemRepository.save(problem);
    }

    @Override
    @Transactional
    public CodingProblem updateProblem(int problemId, CodingProblemRequestDTO dto) {
        CodingProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException("문제 없음"));
        problem.update(
                dto.getProblemTitle(),
                dto.getProblemUrl(),
                dto.getDifficulty(),
                dto.getContent(),
                dto.getInput(),
                dto.getOutput(),
                dto.getConstraints()
        );
        return problem;
    }

    @Override
    @Transactional
    public void deleteProblem(int problemId) {
        CodingProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException("문제 없음"));
        problem.delete();
    }
}
