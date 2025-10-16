package com.threego.algo.studyrecruit.command.application.service;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.aop.IncreasePoint;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRole;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.member.command.domain.repository.MemberRoleRepository;
import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitPostCreateDTO;
import com.threego.algo.studyrecruit.command.application.dto.update.StudyRecruitPostUpdateDTO;
import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitPost;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import com.threego.algo.studyrecruit.command.domain.repository.StudyRecruitPostRepository;
import com.threego.algo.studyrecruit.query.dao.StudyRecruitPostMapper;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitMemberDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRecruitPostServiceImpl implements StudyRecruitPostService {

    private final StudyRecruitPostRepository studyRecruitPostRepository;
    private final MemberRepository memberRepository;
    private final StudyRecruitPostMapper studyRecruitPostMapper;
    private final MemberRoleRepository memberRoleRepository;

    @Override
    @IncreasePoint(amount = 1)
    public ResponseEntity<String> createPost(int memberId, StudyRecruitPostCreateDTO request) {
        try {
            // 1. 회원 존재 여부 확인
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

            // 2. Entity 생성 (DateTimeUtils 사용)
            StudyRecruitPost studyRecruitPost = new StudyRecruitPost(
                    member,
                    request.getTitle(),
                    request.getContent(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getExpiresAt(),
                    request.getCapacity(),
                    DateTimeUtils.nowDateTime()
            );

            // 3. 공개여부 설정
            studyRecruitPost.setVisibility(request.getVisibility());

            // 4. 저장
            studyRecruitPostRepository.save(studyRecruitPost);

            return ResponseEntity.status(HttpStatus.CREATED).body("모집글이 성공적으로 등록되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모집글 등록 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> updatePost(int postId, int memberId, StudyRecruitPostUpdateDTO request) {
        try {
            // 1. 작성자 권한 확인
            StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                    .findByIdAndMemberIdAndVisibility(postId, memberId, "Y")
                    .orElseThrow(() -> new IllegalArgumentException("수정 권한이 없거나 존재하지 않는 모집글입니다."));

            // 2. Entity 업데이트 (DateTimeUtils 사용)
            studyRecruitPost.setTitle(request.getTitle());
            studyRecruitPost.setContent(request.getContent());
            studyRecruitPost.setStartDate(request.getStartDate());
            studyRecruitPost.setEndDate(request.getEndDate());
            studyRecruitPost.setExpiresAt(request.getExpiresAt());
            studyRecruitPost.setCapacity(request.getCapacity());
            studyRecruitPost.setVisibility(request.getVisibility());
            studyRecruitPost.setUpdatedAt(DateTimeUtils.nowDateTime());

            // 3. 저장 (더티 체킹으로 자동 업데이트)
            studyRecruitPostRepository.save(studyRecruitPost);

            return ResponseEntity.ok("모집글이 성공적으로 수정되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모집글 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> deletePost(int postId, int memberId) {
        try {
            // 1. 작성자 권한 확인
            StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                    .findByIdAndMemberIdAndVisibility(postId, memberId, "Y")
                    .orElseThrow(() -> new IllegalArgumentException("삭제 권한이 없거나 존재하지 않는 모집글입니다."));

            // 2. Soft Delete 처리 (DateTimeUtils 사용)
            studyRecruitPost.softDelete();
            studyRecruitPost.setUpdatedAt(DateTimeUtils.nowDateTime());

            // 3. 저장 (더티 체킹으로 자동 업데이트)
            studyRecruitPostRepository.save(studyRecruitPost);

            return ResponseEntity.ok("모집글이 성공적으로 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모집글 삭제 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> closeRecruitment(int postId, int memberId) {
        try {
            // 1. 작성자 권한 확인
            StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                    .findByIdAndMemberIdAndVisibility(postId, memberId, "Y")
                    .orElseThrow(() -> new IllegalArgumentException("마감 권한이 없거나 존재하지 않는 모집글입니다."));

            // 2. 이미 마감된 상태인지 확인
            if (studyRecruitPost.getStatus() == RecruitStatus.CLOSED) {
                return ResponseEntity.badRequest().body("이미 마감된 모집글입니다.");
            }

            // 3. 모집 마감 처리 (DateTimeUtils 사용)
            studyRecruitPost.closeRecruitment();
            studyRecruitPost.setUpdatedAt(DateTimeUtils.nowDateTime());

            // 4. 저장 (더티 체킹으로 자동 업데이트)
            studyRecruitPostRepository.save(studyRecruitPost);

            return ResponseEntity.ok("모집이 성공적으로 마감되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모집 마감 중 오류가 발생했습니다.");
        }
    }

    @Override
    public List<StudyRecruitMemberDTO> findStudyRecruitMembers(int postId) {
        return studyRecruitPostMapper.selectStudyRecruitMembers(postId);
    }

    @Override
    public ResponseEntity<String> adminDeletePost(int postId, int adminId) {
        try {
            // 1. 관리자 권한 확인
            if (!isAdmin(adminId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
            }

            // 2. 모집 게시물 존재 여부 확인
            StudyRecruitPost post = studyRecruitPostRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("모집 게시물을 찾을 수 없습니다."));

            // 3. 이미 삭제된 게시물인지 확인
            if (post.getVisibility().equals("N")) {
                return ResponseEntity.badRequest().body("이미 삭제된 게시물입니다.");
            }

            // 4. 소프트 딜리트 (VISIBILITY: Y → N)
            post.softDelete();
            studyRecruitPostRepository.save(post);

            return ResponseEntity.ok("모집 게시물이 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모집 게시물 삭제 중 오류가 발생했습니다.");
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
}