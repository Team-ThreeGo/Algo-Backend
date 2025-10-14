package com.threego.algo.study.command.application.service;

import com.threego.algo.common.exception.ErrorCode;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.study.command.application.dto.create.StudyCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyUpdateDTO;
import com.threego.algo.study.command.domain.aggregate.Study;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.aggregate.enums.StudyRole;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.command.domain.repository.StudyRepository;
import com.threego.algo.study.exception.StudyExceptions.*;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitPostService;
import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitPost;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import com.threego.algo.studyrecruit.command.domain.repository.StudyRecruitPostRepository;
import com.threego.algo.studyrecruit.exception.RecruitExceptions.*;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyServiceImpl implements StudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRecruitPostRepository studyRecruitPostRepository;
    private final StudyRecruitPostService studyRecruitPostService;

    /* 설명. 스터디 생성 */
    @Override
    public void createStudyFromRecruit(int authorId, int postId, StudyCreateDTO request) {
        // 1. 모집글 존재 여부 및 권한 확인
        StudyRecruitPost recruitPost = studyRecruitPostRepository
                .findByIdAndMemberIdAndVisibility(postId, authorId, "Y")
                .orElseThrow(RecruitPostNotAuthorizedException::new);

        // 2. 모집글 상태 확인 (CLOSED 상태여야 함)
        if (recruitPost.getStatus() != RecruitStatus.CLOSED) {
            throw new RecruitPostNotClosedException();
        }

        // 3. 이미 스터디가 생성되었는지 확인
        if (studyRepository.existsByRecruitPostId(postId)) {
            throw new StudyAlreadyExistsException();
        }

        // 4. MyBatis로 참가 신청자 목록 조회 후 승인된 멤버 필터링
        List<StudyRecruitMemberDTO> allApplicants = studyRecruitPostService.findStudyRecruitMembers(postId);
        List<StudyRecruitMemberDTO> approvedApplicants = allApplicants.stream()
                .filter(applicant -> "APPROVED".equals(applicant.getStatus()))
                .collect(Collectors.toList());

        // 5. 스터디 그룹 생성
        Study study = new Study(
                postId,
                request.getName(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate()
        );
        Study savedStudy = studyRepository.save(study);

        // 6. 스터디 멤버 추가 (작성자를 리더로)
        StudyMember leader = new StudyMember(savedStudy, recruitPost.getMember(), StudyRole.LEADER);
        studyMemberRepository.save(leader);

        // 7. 승인된 신청자들을 멤버로 추가
        for (StudyRecruitMemberDTO approvedApplicant : approvedApplicants) {
            Member member = memberRepository.findById(Math.toIntExact(approvedApplicant.getMemberId()))
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + approvedApplicant.getMemberId()));

            StudyMember studyMember = new StudyMember(savedStudy, member, StudyRole.MEMBER);
            studyMemberRepository.save(studyMember);
        }
    }

    /* 설명. 스터디 정보 수정 */
    @Override
    public void updateStudy(int studyId, int leaderId, StudyUpdateDTO request) {
        // 1. 스터디 존재 여부 확인
        Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        // 2. 리더 권한 확인
        boolean isLeader = studyMemberRepository.existsByStudyIdAndMemberIdAndRole(
                studyId, leaderId, StudyRole.LEADER);

        if (!isLeader) {
            throw new StudyNotLeaderException("스터디 정보를 수정할 권한이 없습니다. (그룹장만 가능)");
        }

        // 3. 스터디 정보 업데이트
        study.updateStudy(request.getName(), request.getDescription(), request.getEndDate());
    }

    /* 설명. 스터디 그룹 삭제 */
    @Override
    public void deleteStudy(int studyId, int leaderId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyNotFoundException("스터디 그룹을 찾을 수 없습니다."));

        Member leader = memberRepository.findById(leaderId)
                .orElseThrow(() -> new IllegalArgumentException("그룹장을 찾을 수 없습니다."));

        StudyMember leaderMember = (StudyMember) studyMemberRepository.findByStudyAndMember(study, leader)
                .orElseThrow(() -> new IllegalArgumentException("스터디 멤버를 찾을 수 없습니다."));

        if (leaderMember.getRole() != StudyRole.LEADER) {
            throw new StudyNotLeaderException("그룹장만 그룹을 삭제할 수 있습니다.");
        }

        // 활동 중인 그룹원 확인 (LEADER, MEMBER 상태만 카운트)
        long activeMemberCount = studyMemberRepository.countByStudyAndRoleIn(
                study,
                Arrays.asList(StudyRole.LEADER, StudyRole.MEMBER)
        );

        if (activeMemberCount > 1) {  // 그룹장 포함이므로 1보다 크면 다른 멤버 존재
            throw new StudyHasActiveMembersException();
        }

        studyMemberRepository.delete(leaderMember);
        studyRepository.delete(study);
    }
}