package com.threego.algo.study.query.service;


import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.query.dao.StudyMemberMapper;
import com.threego.algo.study.query.dto.StudyMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyMemberQueryServiceImpl implements StudyMemberQueryService {
    private final StudyMemberMapper studyMemberMapper;
    private final StudyMemberRepository studyMemberRepository;

    @Override
    public List<StudyMemberDTO> findAllStudyMember(int studyId, int memberId) {
        // 스터디 멤버십 검증
        validateStudyMemberAccess(studyId, memberId);

        return studyMemberMapper.selectAllStudyMember(studyId);
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
