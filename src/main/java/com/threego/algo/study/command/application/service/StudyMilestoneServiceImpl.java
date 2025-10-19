package com.threego.algo.study.command.application.service;

import com.threego.algo.study.command.application.dto.create.StudyMilestoneCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyMilestoneUpdateDTO;
import com.threego.algo.study.command.domain.aggregate.StudyMilestone;
import com.threego.algo.study.command.domain.aggregate.StudyRoadmap;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.command.domain.repository.StudyMilestoneRepository;
import com.threego.algo.study.command.domain.repository.StudyRoadmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyMilestoneServiceImpl implements StudyMilestoneService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyRoadmapRepository studyRoadmapRepository;
    private final StudyMilestoneRepository studyMilestoneRepository;

    /* 설명. 마일스톤 등록 */
    @Override
    public ResponseEntity<String> createMilestone(int roadmapId, int leaderId, StudyMilestoneCreateDTO request) {
        try {
            // 1. 로드맵 존재 여부 확인
            StudyRoadmap roadmap = studyRoadmapRepository.findById(roadmapId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로드맵입니다."));

            // 2. 권한 확인 - 로드맵 작성자(스터디장)와 현재 사용자가 같은지 확인
            if (roadmap.getMemberId() != (leaderId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("마일스톤을 생성할 권한이 없습니다. (그룹장만 가능)");
            }

            // 3. 순서 결정
            int order = request.getOrder();


            // 4. 마일스톤 생성
            StudyMilestone milestone = new StudyMilestone(roadmapId, request.getTitle(), request.getDescription(), order);
            studyMilestoneRepository.save(milestone);

            return ResponseEntity.status(HttpStatus.CREATED).body("마일스톤이 성공적으로 등록되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("마일스톤 등록 중 오류가 발생했습니다.");
        }
    }

    /* 설명. 마일스톤 수정 */
    @Override
    public ResponseEntity<String> updateMilestone(int milestoneId, int leaderId, StudyMilestoneUpdateDTO request) {
        try {
            // 1. 마일스톤 존재 여부 확인
            StudyMilestone milestone = studyMilestoneRepository.findById(milestoneId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마일스톤입니다."));

            // 2. 권한 확인 - 마일스톤 작성자와 현재 사용자가 같은지 확인
            int authorId = getMilestoneAuthorId(milestoneId);
            if (authorId != (leaderId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("마일스톤을 수정할 권한이 없습니다. (그룹장만 가능)");
            }

            // 3. 마일스톤 업데이트
            milestone.updateMilestone(request.getTitle(), request.getDescription(), request.getOrder());

            return ResponseEntity.ok("마일스톤이 성공적으로 수정되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("마일스톤 수정 중 오류가 발생했습니다.");
        }
    }

    /* 설명. 마일스톤 삭제 */
    @Override
    public ResponseEntity<String> deleteMilestone(int milestoneId, int leaderId) {
        try {
            // 1. 마일스톤 존재 여부 확인
            StudyMilestone milestone = studyMilestoneRepository.findById(milestoneId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마일스톤입니다."));

            // 2. 권한 확인 - 마일스톤 작성자와 현재 사용자가 같은지 확인
            int authorId = getMilestoneAuthorId(milestoneId);
            if (authorId != (leaderId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("마일스톤을 삭제할 권한이 없습니다. (그룹장만 가능)");
            }

            // 3. 하드 딜리트
            studyMilestoneRepository.delete(milestone);

            return ResponseEntity.ok("마일스톤이 성공적으로 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("마일스톤 삭제 중 오류가 발생했습니다.");
        }
    }

    /* 설명. 마일스톤 작성자 ID 조회 - 로드맵 작성자와 동일 */
    public int getMilestoneAuthorId(int milestoneId) {
        StudyMilestone milestone = studyMilestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 마일스톤입니다."));

        StudyRoadmap roadmap = studyRoadmapRepository.findById(milestone.getRoadmapId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로드맵입니다."));

        return roadmap.getMemberId(); // 로드맵 작성자 = 마일스톤 작성자 (스터디장)
    }
}