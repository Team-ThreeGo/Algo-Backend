package com.threego.algo.study.query.service;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.aggregate.StudyRoadmap;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.command.domain.repository.StudyRoadmapRepository;
import com.threego.algo.study.query.dao.StudyRoadmapMapper;
import com.threego.algo.study.query.dto.StudyRoadmapDTO;
import com.threego.algo.study.query.dto.StudyRoadmapDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRoadmapQueryServiceImpl implements StudyRoadmapQueryService {
    private final StudyRoadmapMapper studyRoadmapMapper;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRoadmapRepository studyRoadmapRepository;

    @Override
    public List<StudyRoadmapDTO> findAllStudyRoadmap(int studyId, int memberId) {
        // 스터디 멤버십 검증
        validateStudyMemberAccess(studyId, memberId);

        return studyRoadmapMapper.selectAllStudyRoadmap(studyId);
    }

    @Override
    public StudyRoadmapDetailDTO findStudyRoadmapDetail(int roadmapId, int memberId) {
        // 로드맵 Entity로 studyId 획득
        StudyRoadmap roadmapEntity = studyRoadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "로드맵을 찾을 수 없습니다."));

        // 스터디 멤버십 검증
        validateStudyMemberAccess(roadmapEntity.getStudyId(), memberId);

        // 로드맵 상세 조회 (마일스톤 포함)
        StudyRoadmapDetailDTO roadmap = studyRoadmapMapper.selectStudyRoadmapDetail(roadmapId);
        if (roadmap == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "로드맵을 찾을 수 없습니다.");
        }

        return roadmap;
    }

    // ========== Helper Methods ==========

    private void validateStudyMemberAccess(int studyId, int memberId) {
        StudyMember studyMember = (StudyMember) studyMemberRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_MEMBER_NOT_FOUND));

        if (!studyMember.isActive()) {
            throw new BusinessException(ErrorCode.STUDY_ACCESS_DENIED);
        }
    }
}
