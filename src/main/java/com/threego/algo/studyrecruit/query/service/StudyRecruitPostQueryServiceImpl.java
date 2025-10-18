package com.threego.algo.studyrecruit.query.service;

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
    public StudyRecruitPostListResponseDTO findStudyRecruitList(StudyRecruitSearchDTO searchDto) {
        List<StudyRecruitPostDTO> posts = studyRecruitPostMapper.selectStudyRecruitList(searchDto);
        long totalCount = studyRecruitPostMapper.countStudyRecruitList(searchDto);

        return StudyRecruitPostListResponseDTO.builder()
                .posts(posts)
                .totalCount(totalCount)
                .build();
    }

    /* 설명. 스터디 모집글 상세 조회 */
    @Override
    public StudyRecruitDetailDTO findStudyRecruitDetail(int postId) {
        return studyRecruitPostMapper.selectStudyRecruitDetail(postId);
    }

    /* 설명. 스터디 모집 게시물의 댓글 조회 */
    @Override
    public List<StudyRecruitCommentDTO> findStudyRecruitComments(int postId) {
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
        return studyRecruitPostMapper.selectStudyRecruitDetailIncludeHidden(postId);
    }


    /* 설명. 관리자: 숨김 처리된 댓글 리스트 조회 */
    @Override
    public List<StudyRecruitCommentDTO> findAllStudyRecruitCommentsIncludeHidden(StudyRecruitSearchDTO searchDto) {
        return studyRecruitCommentMapper.selectAllStudyRecruitCommentsIncludeHidden(searchDto);
    }

    /* 설명. 관리자: 숨김 처리된 댓글 조회 */
    @Override
    public List<StudyRecruitCommentDTO> findStudyRecruitCommentsIncludeHidden(int postId) {
        return studyRecruitCommentMapper.selectStudyRecruitCommentsIncludeHidden(postId);
    }

}
