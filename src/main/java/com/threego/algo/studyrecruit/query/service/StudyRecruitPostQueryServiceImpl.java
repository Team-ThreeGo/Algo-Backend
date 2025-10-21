package com.threego.algo.studyrecruit.query.service;

import com.threego.algo.studyrecruit.exception.RecruitExceptions.RecruitPostNotFoundException;
import com.threego.algo.studyrecruit.query.dao.StudyRecruitCommentMapper;
import com.threego.algo.studyrecruit.query.dao.StudyRecruitPostMapper;
import com.threego.algo.studyrecruit.query.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRecruitPostQueryServiceImpl implements StudyRecruitPostQueryService {
    private final StudyRecruitPostMapper studyRecruitPostMapper;
    private final StudyRecruitCommentMapper studyRecruitCommentMapper;

    /* 설명. 스터디 모집글 전체 목록 조회 */
    @Override
    public List<StudyRecruitPostDTO> findStudyRecruitList(StudyRecruitSearchDTO searchDto) {
        return studyRecruitPostMapper.selectStudyRecruitList(searchDto);
    }

    /* 설명. 스터디 모집글 상세 조회 */
    @Override
    public StudyRecruitDetailDTO findStudyRecruitDetail(int postId) {
        StudyRecruitDetailDTO result = studyRecruitPostMapper.selectStudyRecruitDetail(postId);
        if (result == null) {
            throw new RecruitPostNotFoundException();
        }
        return result;
    }

    /* 설명. 스터디 모집 게시물의 댓글 조회 */
    @Override
    public List<StudyRecruitCommentDTO> findStudyRecruitComments(int postId) {
        // 게시물 존재 여부 확인
        StudyRecruitDetailDTO post = studyRecruitPostMapper.selectStudyRecruitDetail(postId);
        if (post == null) {
            throw new RecruitPostNotFoundException();
        }
        return studyRecruitCommentMapper.selectStudyRecruitComments(postId);
    }

    /* 설명. 스터디 모집 참여 멤버 목록 조회 */
    @Override
    public List<StudyRecruitMemberDTO> findStudyRecruitMembers(int postId) {
        return studyRecruitPostMapper.selectStudyRecruitMembers(postId);
    }

    /* 설명. 관리자: 숨김 처리된 게시물 목록 조회 */
    @Override
    public List<StudyRecruitPostDTO> findStudyRecruitListIncludeHidden(StudyRecruitSearchDTO searchDto) {
        return studyRecruitPostMapper.selectStudyRecruitListIncludeHidden(searchDto);
    }

    /* 설명. 관리자: 숨김 처리된 게시물 상세 조회 */
    @Override
    public StudyRecruitDetailDTO findStudyRecruitDetailIncludeHidden(int postId) {
        StudyRecruitDetailDTO result = studyRecruitPostMapper.selectStudyRecruitDetailIncludeHidden(postId);
        if (result == null) {
            throw new RecruitPostNotFoundException();
        }
        return result;
    }


    /* 설명. 관리자: 숨김 처리된 댓글 리스트 조회 */
    @Override
    public List<StudyRecruitCommentDTO> findAllStudyRecruitCommentsIncludeHidden(StudyRecruitSearchDTO searchDto) {
        return studyRecruitCommentMapper.selectAllStudyRecruitCommentsIncludeHidden(searchDto);
    }

    /* 설명. 관리자: 숨김 처리된 댓글 조회 */
    @Override
    public List<StudyRecruitCommentDTO> findStudyRecruitCommentsIncludeHidden(int postId) {
        // 게시물 존재 여부 확인 (숨김 포함)
        StudyRecruitDetailDTO post = studyRecruitPostMapper.selectStudyRecruitDetailIncludeHidden(postId);
        if (post == null) {
            throw new RecruitPostNotFoundException();
        }
        return studyRecruitCommentMapper.selectStudyRecruitCommentsIncludeHidden(postId);
    }

}
