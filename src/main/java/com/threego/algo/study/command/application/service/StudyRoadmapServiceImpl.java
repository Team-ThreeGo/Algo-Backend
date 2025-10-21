package com.threego.algo.study.command.application.service;

import com.threego.algo.study.command.application.dto.create.StudyRoadmapCreateDTO;
import com.threego.algo.study.command.application.dto.update.StudyRoadmapUpdateDTO;
import com.threego.algo.study.command.domain.aggregate.StudyRoadmap;
import com.threego.algo.study.command.domain.aggregate.enums.StudyRole;
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
public class StudyRoadmapServiceImpl implements  StudyRoadmapService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyRoadmapRepository studyRoadmapRepository;
    private final StudyMilestoneRepository studyMilestoneRepository;

    /* 설명. 스터디 로드맵 생성 */
    @Override
    public ResponseEntity<String> createRoadmap(int studyId, int leaderId, StudyRoadmapCreateDTO request) {
        try {

            // 1. 리더 권한 확인
            if (!isStudyLeader(studyId, leaderId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("로드맵을 생성할 권한이 없습니다. (그룹장만 가능)");
            }

            // 2. 순서 결정 (지정하지 않으면 기본값 1)
            int order = request.getOrder();

            // 3. 로드맵 생성
            StudyRoadmap roadmap = new StudyRoadmap(studyId, leaderId,request.getTitle(), request.getDescription(), order);
            studyRoadmapRepository.save(roadmap);

            return ResponseEntity.status(HttpStatus.CREATED).body("로드맵이 성공적으로 등록되었습니다.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로드맵 등록 중 오류가 발생했습니다.");
        }
    }

    /* 설명. 스터리 로드맵 수정 */
    @Override
    public ResponseEntity<String> updateRoadmap(int roadmapId, int leaderId, StudyRoadmapUpdateDTO request) {
        try {
            // 1. 로드맵 존재 여부 확인
            StudyRoadmap roadmap = studyRoadmapRepository.findById(roadmapId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로드맵입니다."));

            // 2. 리더 권한 확인
            if (!isStudyLeader(roadmap.getStudyId(), leaderId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("로드맵을 수정할 권한이 없습니다. (그룹장만 가능)");
            }

            // 3. 로드맵 업데이트
            roadmap.updateRoadmap(request.getTitle(), request.getDescription(), request.getOrder());
            studyRoadmapRepository.save(roadmap);

            return ResponseEntity.ok("로드맵이 성공적으로 수정되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로드맵 수정 중 오류가 발생했습니다.");
        }
    }

    /* 설명. 스터디 로드맵 삭제 */
    @Override
    public ResponseEntity<String> deleteRoadmap(int roadmapId, int leaderId) {
        try {
            // 1. 로드맵 존재 여부 확인
            StudyRoadmap roadmap = studyRoadmapRepository.findById(roadmapId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로드맵입니다."));

            // 2. 리더 권한 확인
            if (!isStudyLeader(roadmap.getStudyId(), leaderId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("로드맵을 삭제할 권한이 없습니다. (그룹장만 가능)");
            }

            // 3. 하위 마일스톤 존재 여부 확인
            if (studyMilestoneRepository.existsByRoadmapId(roadmapId)) {
                return ResponseEntity.badRequest()
                        .body("하위 마일스톤이 존재하여 로드맵을 삭제할 수 없습니다. 마일스톤을 먼저 삭제해주세요.");
            }

            // 4. 하드 딜리트
            studyRoadmapRepository.delete(roadmap);

            return ResponseEntity.ok("로드맵이 성공적으로 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로드맵 삭제 중 오류가 발생했습니다.");
        }
    }

    /* 설명. 스터디 리더 권한 확인 */
    private boolean isStudyLeader(int studyId, int memberId) {
        return studyMemberRepository.existsByStudyIdAndMemberIdAndRole(studyId, memberId, StudyRole.LEADER);
    }
}
