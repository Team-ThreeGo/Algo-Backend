package com.threego.algo.study.command.application.service;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;
import com.threego.algo.member.command.domain.aggregate.MemberRole;
import com.threego.algo.member.command.domain.repository.MemberRoleRepository;
import com.threego.algo.study.command.application.dto.create.StudyCommentCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyCommentUpdateDTO;
import com.threego.algo.study.command.domain.aggregate.StudyComment;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.aggregate.StudyPost;
import com.threego.algo.study.command.domain.aggregate.enums.StudyRole;
import com.threego.algo.study.command.domain.repository.StudyCommentRepository;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.command.domain.repository.StudyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyCommentServiceImpl implements StudyCommentService {

    private final StudyCommentRepository studyCommentRepository;
    private final StudyPostRepository studyPostRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Override
    public ResponseEntity<String> createComment(int postId, int memberId, StudyCommentCreateDTO request) {
        try {
            // 1. 게시물 존재 여부 확인
            StudyPost post = studyPostRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

            // 2. 작성자가 스터디 멤버인지 확인
            validateStudyMemberAccess(post.getStudyId(), memberId);

            // 3. 댓글 생성
            StudyComment comment;
            if (request.getParentId() != null) {
                comment = new StudyComment(postId, memberId, request.getParentId(), request.getContent());
            } else {
                comment = new StudyComment(postId, memberId, request.getContent());
            }
            studyCommentRepository.save(comment);

            // 4. 게시물 댓글 수 증가
            post.increaseCommentCount();

            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 등록되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 등록 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> updateComment(int commentId, int memberId, StudyCommentUpdateDTO request) {
        try {
            // 1. 댓글 존재 여부 확인
            StudyComment comment = studyCommentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

            // 2. 작성자 권한 확인
            if (comment.getMemberId() != (memberId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("댓글을 수정할 권한이 없습니다.");
            }

            // 3. 댓글 수정
            comment.updateComment(request.getContent());

            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> deleteComment(int commentId, int memberId) {
        try {
            // 1. 댓글 존재 여부 확인
            StudyComment comment = studyCommentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

            // 2. 작성자 권한 확인
            if (comment.getMemberId()!=(memberId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("댓글을 삭제할 권한이 없습니다.");
            }

            // 3. 소프트 딜리트
            comment.softDelete();

            // 4. 게시물 댓글 수 감소
            StudyPost post = studyPostRepository.findById(comment.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
            post.decreaseCommentCount();

            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 삭제 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> adminDeleteComment(int commentId, int adminId) {
        try {
            // 1. 관리자 권한 확인
            if (!isAdmin(adminId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
            }

            // 2. 스터디 댓글 존재 여부 확인
            StudyComment comment = studyCommentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("스터디 댓글을 찾을 수 없습니다."));

            // 3. 소프트 딜리트 (VISIBILITY: Y → N)
            comment.softDelete();
            studyCommentRepository.save(comment);

            return ResponseEntity.ok("스터디 댓글이 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스터디 댓글 삭제 중 오류가 발생했습니다.");
        }
    }

    private void validateStudyMemberAccess(int studyId, int memberId) {
        StudyMember studyMember = getStudyMember(studyId, memberId);

        if (!studyMember.isActive()) {
            throw new BusinessException(ErrorCode.STUDY_ACCESS_DENIED);
        }
    }

    private boolean isAdmin(int memberId) {
        try {
            int roleId = memberRoleRepository.getRoleIdByMemberId(memberId);
            return roleId == 2;
        } catch (Exception e) {
            return false;
        }
    }

    private StudyMember getStudyMember(int studyId, int memberId) {
        return (StudyMember) studyMemberRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_FOUND));
    }

}