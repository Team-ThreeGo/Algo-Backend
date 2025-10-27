package com.threego.algo.studyrecruit.command.application.service;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.aop.IncreasePoint;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.member.command.domain.repository.MemberRoleRepository;
import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitPostCreateDTO;
import com.threego.algo.studyrecruit.command.application.dto.update.StudyRecruitPostUpdateDTO;
import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitPost;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import com.threego.algo.studyrecruit.command.domain.repository.StudyRecruitPostRepository;
import com.threego.algo.studyrecruit.exception.RecruitExceptions.*;
import com.threego.algo.studyrecruit.query.dao.StudyRecruitPostMapper;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitMemberDTO;
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
    public void createPost(int memberId, StudyRecruitPostCreateDTO request) {
        // 1. 회원 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. Entity 생성 (expiresAt과 visibility는 Entity에서 자동 설정)
        StudyRecruitPost studyRecruitPost = new StudyRecruitPost(
                member,
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate(),
                request.getCapacity()
        );

        // 3. 저장
        studyRecruitPostRepository.save(studyRecruitPost);
    }

    @Override
    public void updatePost(int postId, int memberId, StudyRecruitPostUpdateDTO request) {
        // 1. 작성자 권한 확인
        StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                .findByIdAndMemberIdAndVisibility(postId, memberId, "Y")
                .orElseThrow(RecruitPostNotAuthorizedException::new);

        // 2. Entity 업데이트 (expiresAt은 Entity에서 자동 설정)
        studyRecruitPost.updatePost(
                request.getTitle(),
                request.getContent(),
                request.getStartDate(),
                request.getEndDate(),
                request.getCapacity()
        );
    }

    @Override
    public void deletePost(int postId, int memberId) {
        // 1. 작성자 권한 확인
        StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                .findByIdAndMemberIdAndVisibility(postId, memberId, "Y")
                .orElseThrow(RecruitPostNotAuthorizedException::new);

        // 2. Soft Delete 처리 (DateTimeUtils 사용)
        studyRecruitPost.softDelete();
        studyRecruitPost.setUpdatedAt(DateTimeUtils.nowDateTime());

    }

    @Override
    public void closeRecruitment(int postId, int memberId) {
        // 1. 작성자 권한 확인
        StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                .findByIdAndMemberIdAndVisibility(postId, memberId, "Y")
                .orElseThrow(RecruitPostNotAuthorizedException::new);

        // 2. 이미 마감된 상태인지 확인
        if (studyRecruitPost.getStatus() == RecruitStatus.CLOSED) {
            throw new RecruitPostAlreadyClosedException();
        }

        // 3. 모집 마감 처리 (DateTimeUtils 사용)
        studyRecruitPost.closeRecruitment();
        studyRecruitPost.setUpdatedAt(DateTimeUtils.nowDateTime());

    }

    @Override
    public List<StudyRecruitMemberDTO> findStudyRecruitMembers(int postId) {
        return studyRecruitPostMapper.selectStudyRecruitMembers(postId);
    }

    @Override
    public void adminDeletePost(int postId, int adminId) {
        // 1. 관리자 권한 확인
        if (!isAdmin(adminId)) {
            throw new RecruitPostNotAuthorizedException();
        }

        // 2. 모집 게시물 존재 여부 확인
        StudyRecruitPost post = studyRecruitPostRepository.findById(postId)
                .orElseThrow(RecruitPostNotFoundException::new);

        // 3. 이미 삭제된 게시물인지 확인
        if (post.getVisibility().equals("N")) {
            throw new RecruitPostAlreadyDeletedException();
        }

        // 4. 소프트 딜리트 (VISIBILITY: Y → N)
        post.softDelete();
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