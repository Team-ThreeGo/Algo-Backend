package com.threego.algo.studyrecruit.command.application.service;

import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitMember;
import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitPost;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import com.threego.algo.studyrecruit.command.domain.repository.StudyRecruitMemberRepository;
import com.threego.algo.studyrecruit.command.domain.repository.StudyRecruitPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyRecruitMemberServiceImpl implements StudyRecruitMemberService {

    private final StudyRecruitMemberRepository studyRecruitMemberRepository;
    private final StudyRecruitPostRepository studyRecruitPostRepository;
    private final MemberRepository memberRepository;

    @Override
    public ResponseEntity<String> applyToStudy(int postId, int memberId, com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitMemberCreateDTO request) {
        try {
            log.info("스터디 참가신청 시작 - postId: {}, memberId: {}", postId, memberId);
            // 1. 회원 존재 여부 확인
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));



            // 2. 모집글 존재 여부 및 모집 상태 확인
            StudyRecruitPost studyRecruitPost = studyRecruitPostRepository
                    .findById(postId)
                    .filter(post -> post.getVisibility().equals("Y"))
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 공개되지 않은 모집글입니다."));

            // 3. 모집 상태 확인
            if (studyRecruitPost.getStatus() != RecruitStatus.OPEN) {
                return ResponseEntity.badRequest().body("모집이 마감된 스터디입니다.");
            }

            // 4. 중복 신청 확인
            if (studyRecruitMemberRepository.existsByStudyRecruitPostIdAndMemberId(postId, memberId)) {
                return ResponseEntity.badRequest().body("이미 신청한 스터디입니다.");
            }

            // 5. Entity 생성 및 저장
            StudyRecruitMember application = new StudyRecruitMember(studyRecruitPost, member, request.getApplicant());

            studyRecruitMemberRepository.save(application);

            return ResponseEntity.status(HttpStatus.CREATED).body("스터디 참가 신청이 완료되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("참가 신청 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> cancelApplication(int joinId, int memberId) {
        try {
            // 1. 신청자 본인 확인
            StudyRecruitMember application = studyRecruitMemberRepository
                    .findByIdAndMemberId(joinId, memberId)
                    .orElseThrow(() -> new IllegalArgumentException("취소할 수 있는 신청이 없습니다."));

            // 2. Hard Delete 처리
            studyRecruitMemberRepository.delete(application);

            return ResponseEntity.ok("스터디 참가 신청이 취소되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 취소 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> acceptApplication(int joinId, int authorId) {
        try {
            // 1. 모집글 작성자 권한 확인
            StudyRecruitMember application = studyRecruitMemberRepository
                    .findByIdAndAuthor(joinId, authorId)
                    .orElseThrow(() -> new IllegalArgumentException("승인 권한이 없거나 존재하지 않는 신청입니다."));

            // 2. 이미 처리된 신청인지 확인
            if (!application.isPending()) {
                return ResponseEntity.badRequest().body("이미 처리된 신청입니다.");
            }

            // 3. 신청 승인 처리
            application.approve();
            studyRecruitMemberRepository.save(application);

            return ResponseEntity.ok("스터디 참가 신청이 승인되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 승인 중 오류가 발생했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> rejectApplication(int joinId, int authorId) {
        try {
            // 1. 모집글 작성자 권한 확인
            StudyRecruitMember application = studyRecruitMemberRepository
                    .findByIdAndAuthor(joinId, authorId)
                    .orElseThrow(() -> new IllegalArgumentException("거절 권한이 없거나 존재하지 않는 신청입니다."));

            // 2. 이미 처리된 신청인지 확인
            if (!application.isPending()) {
                return ResponseEntity.badRequest().body("이미 처리된 신청입니다.");
            }

            // 3. 신청 거절 처리
            application.reject();
            studyRecruitMemberRepository.save(application);

            return ResponseEntity.ok("스터디 참가 신청이 거절되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 거절 중 오류가 발생했습니다.");
        }
    }
}