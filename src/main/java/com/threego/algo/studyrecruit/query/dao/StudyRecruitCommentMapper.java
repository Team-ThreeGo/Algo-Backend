package com.threego.algo.studyrecruit.query.dao;

import com.threego.algo.studyrecruit.query.dto.StudyRecruitCommentDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyRecruitCommentMapper {

    /* 설명. 스터디 모집글 댓글 목록 조회 */
    List<StudyRecruitCommentDTO> selectStudyRecruitComments(Long postId);

    /* 설명. 관리자: 숨김 처리된 댓글 리스트 조회 */
    List<StudyRecruitCommentDTO> selectAllStudyRecruitCommentsIncludeHidden(StudyRecruitSearchDTO searchDto);

    /* 설명. 관리자: 숨김 처리된 댓글 조회 */
    List<StudyRecruitCommentDTO> selectStudyRecruitCommentsIncludeHidden(Long postId);

}