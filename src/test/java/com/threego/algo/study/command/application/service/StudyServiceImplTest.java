package com.threego.algo.study.command.application.service;

import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRank;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.study.command.application.dto.create.StudyCreateDTO;
import com.threego.algo.study.command.domain.aggregate.Study;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.aggregate.enums.StudyRole;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.command.domain.repository.StudyRepository;
import com.threego.algo.study.exception.StudyExceptions.*;
import com.threego.algo.studyrecruit.command.application.service.StudyRecruitPostService;
import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitPost;
import com.threego.algo.studyrecruit.command.domain.repository.StudyRecruitPostRepository;
import com.threego.algo.studyrecruit.exception.RecruitExceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("StudyService 단위 테스트")
class StudyServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private StudyMemberRepository studyMemberRepository;

    @Mock
    private StudyRecruitPostRepository studyRecruitPostRepository;

    @Mock
    private StudyRecruitPostService studyRecruitPostService;

    @InjectMocks
    private StudyServiceImpl studyService;

    @DisplayName("스터디 생성 성공")
    @Test
    void createStudy_Success() {
        log.info("=== 스터디 생성 성공 테스트 시작 ===");
        // given
        int authorId = 1;
        int postId = 1;

        Member testMember = new Member(
                "test@example.com",
                "password123",
                "testUser",
                new MemberRank(),
                "2025-01-01 10:00:00"
        );
        ReflectionTestUtils.setField(testMember, "id", 1);
        log.info("테스트 멤버 생성 완료: {}", testMember.getNickname());

        StudyRecruitPost testRecruitPost = new StudyRecruitPost(
                testMember,
                "알고리즘 스터디 모집",
                "매일 1문제씩",
                "2025-10-01",
                "2026-01-01",
                "2025-09-30",
                10,
                "2025-09-01 10:00:00"
        );
        ReflectionTestUtils.setField(testRecruitPost, "id", 1);
        testRecruitPost.closeRecruitment();
        log.info("모집글 생성 및 마감 완료: {}", testRecruitPost.getTitle());

        StudyCreateDTO createDTO = new StudyCreateDTO(
                "알고리즘 스터디",
                "매일 1문제씩 풉니다",
                "2025-10-01",
                "2026-01-01"
        );
        log.info("스터디 생성 DTO 준비 완료: {}", createDTO.getName());

        when(studyRecruitPostRepository.findByIdAndMemberIdAndVisibility(postId, authorId, "Y"))
                .thenReturn(Optional.of(testRecruitPost));
        when(studyRepository.existsByRecruitPostId(postId)).thenReturn(false);
        when(studyRecruitPostService.findStudyRecruitMembers(postId))
                .thenReturn(new ArrayList<>());
        when(studyRepository.save(any(Study.class))).thenReturn(new Study());
        log.info("Mock 설정 완료");

        // when
        log.info("스터디 생성 서비스 호출...");
        Assertions.assertDoesNotThrow(() ->
            studyService.createStudyFromRecruit(authorId, postId, createDTO)
        );
        log.info("스터디 생성 성공");

        // then
        log.info("=== 스터디 생성 성공 테스트 완료 ===\n");
    }


    @DisplayName("스터디 생성 실패 - 모집글이 CLOSED 상태가 아닌 경우")
    @Test
    void createStudy_Fail_RecruitNotClosed() {
        log.info("=== 스터디 생성 실패 테스트 시작 (모집 중) ===");

        // given
        int authorId = 1;
        int postId = 1;

        Member testMember = new Member(
                "test@example.com",
                "password123",
                "testUser",
                new MemberRank(),
                "2025-01-01 10:00:00"
        );
        ReflectionTestUtils.setField(testMember, "id", 1);

        StudyRecruitPost openPost = new StudyRecruitPost(
                testMember,
                "알고리즘 스터디 모집",
                "매일 1문제씩",
                "2025-10-01",
                "2026-01-01",
                "2025-09-30",
                10,
                "2025-09-01 10:00:00"
        );
        ReflectionTestUtils.setField(openPost, "id", 1);
        log.info("모집글 상태: {} (CLOSED 아님)", openPost.getStatus());

        StudyCreateDTO createDTO = new StudyCreateDTO(
                "알고리즘 스터디",
                "매일 1문제씩 풉니다",
                "2025-10-01",
                "2026-01-01"
        );

        when(studyRecruitPostRepository.findByIdAndMemberIdAndVisibility(postId, authorId, "Y"))
                .thenReturn(Optional.of(openPost));

        // when & then
        log.info("스터디 생성 시도...");
        Assertions.assertThrows(RecruitPostNotClosedException.class, () ->
            studyService.createStudyFromRecruit(authorId, postId, createDTO)
        );
        log.info("예상대로 RecruitPostNotClosedException 발생");
        log.info("=== 스터디 생성 실패 테스트 완료 ===\n");
    }

    @DisplayName("스터디 삭제 실패 - 활동 중인 멤버가 있는 경우")
    @Test
    void deleteStudy_Fail_HasActiveMembers() {
        log.info("=== 스터디 삭제 실패 테스트 시작 (활동 멤버 있음) ===");

        // given
        int studyId = 1;
        int leaderId = 1;

        Member testMember = new Member(
                "test@example.com",
                "password123",
                "testUser",
                new MemberRank(),
                "2025-01-01 10:00:00"
        );
        ReflectionTestUtils.setField(testMember, "id", 1);

        Study testStudy = new Study(
                1,
                "알고리즘 스터디",
                "매일 1문제씩",
                "2025-01-01 10:00:00",
                "2025-11-11 12:00:00"
        );
        ReflectionTestUtils.setField(testStudy, "id", 1);
        log.info("테스트 스터디 생성: {}", testStudy.getName());

        StudyMember leaderMember = new StudyMember(testStudy, testMember, StudyRole.LEADER);

        when(studyRepository.findById(studyId)).thenReturn(Optional.of(testStudy));
        when(memberRepository.findById(leaderId)).thenReturn(Optional.of(testMember));
        when(studyMemberRepository.findByStudyAndMember(testStudy, testMember))
                .thenReturn(Optional.of(leaderMember));
        when(studyMemberRepository.countByStudyAndRoleIn
                (testStudy, Arrays.asList(StudyRole.LEADER, StudyRole.MEMBER)))
                .thenReturn(3L);
        log.info("활동 중인 멤버 수: 3명 (리더 1명 + 멤버 2명)");

        // when & then
        log.info("스터디 삭제 시도...");
        Assertions.assertThrows(StudyHasActiveMembersException.class, () ->
            studyService.deleteStudy(studyId, leaderId)
        );
        log.info("예상대로 StudyHasActiveMembersException 발생");
        log.info("=== 스터디 삭제 실패 테스트 완료 ===\n");
    }

    @DisplayName("스터디 삭제 성공 - 멤버 전원 강퇴 후 삭제")
    @Test
    void deleteStudy_Success_AfterKickAllMembers() {
        log.info("=== 스터디 삭제 성공 테스트 시작 (멤버 강퇴 후) ===");

        // given
        int studyId = 1;
        int leaderId = 1;

        Member leader = new Member(
                "leader@example.com",
                "password123",
                "leader",
                new MemberRank(),
                "2025-01-01 10:00:00"
        );
        ReflectionTestUtils.setField(leader, "id", 1);

        Study testStudy = new Study(
                1,
                "알고리즘 스터디",
                "매일 1문제씩",
                "2025-01-01 10:00:00",
                "2025-11-11 12:00:00"
        );
        ReflectionTestUtils.setField(testStudy, "id", 1);

        Member member1 = new Member(
                "member1@example.com",
                "password123",
                "member1",
                new MemberRank(),
                "2025-01-01 10:00:00"
        );
        ReflectionTestUtils.setField(member1, "id", 2);

        Member member2 = new Member(
                "member2@example.com",
                "password123",
                "member2",
                new MemberRank(),
                "2025-01-01 10:00:00"
        );
        ReflectionTestUtils.setField(member2, "id", 3);

        StudyMember leaderMember = new StudyMember(testStudy, leader, StudyRole.LEADER);
        StudyMember studyMember1 = new StudyMember(testStudy, member1, StudyRole.MEMBER);
        StudyMember studyMember2 = new StudyMember(testStudy, member2, StudyRole.MEMBER);

        studyMember1.kickOut();
        studyMember2.kickOut();
        log.info("멤버 2명 강퇴 완료");

        when(studyRepository.findById(studyId)).thenReturn(Optional.of(testStudy));
        when(memberRepository.findById(leaderId)).thenReturn(Optional.of(leader));
        when(studyMemberRepository.findByStudyAndMember(testStudy, leader))
                .thenReturn(Optional.of(leaderMember));
        when(studyMemberRepository.countByStudyAndRoleIn(testStudy, Arrays.asList(StudyRole.LEADER, StudyRole.MEMBER)))
                .thenReturn(1L);
        log.info("활동 중인 멤버 수: 1명 (리더만 남음)");

        // when
        log.info("스터디 삭제 시도...");
        Assertions.assertDoesNotThrow(() ->
            studyService.deleteStudy(studyId, leaderId)
        );
        log.info("스터디 삭제 성공");

        // then
        log.info("=== 스터디 삭제 성공 테스트 완료 ===\n");
    }
}
