package com.threego.algo.study.query.service;

import com.threego.algo.common.error.ErrorCode;
import com.threego.algo.common.error.exception.BusinessException;
import com.threego.algo.study.command.domain.aggregate.StudyMember;
import com.threego.algo.study.command.domain.repository.StudyMemberRepository;
import com.threego.algo.study.exception.StudyExceptions.StudyPostNotFoundException;
import com.threego.algo.study.query.dao.StudyPostMapper;
import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyPostQueryServiceImpl implements StudyPostQueryService {

    private final StudyPostMapper studyPostMapper;
    private final StudyMemberRepository studyMemberRepository;

    @Override
    public List<StudyPostDTO> findAllStudyPosts(StudyPostSearchDTO searchDto, int memberId) {
        // 스터디 멤버십 검증
        validateStudyMemberAccess(searchDto.getStudyId(), memberId);

        return studyPostMapper.selectAllStudyPosts(searchDto);
    }

    @Override
    public StudyPostDetailDTO findStudyPostDetail(int postId, int memberId) {
        // 게시물 조회
        StudyPostDetailDTO result = studyPostMapper.selectStudyPostDetail(postId);
        if (result == null) {
            throw new StudyPostNotFoundException();
        }

        // 스터디 멤버십 검증 (postId로부터 studyId 획득)
        validateStudyMemberAccess(result.getStudyId(), memberId);

        return result;
    }

    @Override
    public List<StudyCommentDTO> findStudyPostComments(int postId) {
        // 게시물 존재 여부 확인
        StudyPostDetailDTO post = studyPostMapper.selectStudyPostDetail(postId);
        if (post == null) {
            throw new StudyPostNotFoundException();
        }
        return studyPostMapper.selectStudyPostComments(postId);
    }

    @Override
    public List<StudyPostDTO> findAllHiddenStudyPosts(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllHiddenStudyPosts(searchDto);
    }

    @Override
    public StudyPostDetailDTO findHiddenStudyPostDetail(int postId) {
        StudyPostDetailDTO result = studyPostMapper.selectHiddenStudyPostDetail(postId);
        if (result == null) {
            throw new StudyPostNotFoundException();
        }
        return result;
    }

    @Override
    public List<StudyCommentDTO> findAllHiddenStudyComments(StudyPostSearchDTO searchDto) {
        return studyPostMapper.selectAllHiddenStudyComments(searchDto);
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
