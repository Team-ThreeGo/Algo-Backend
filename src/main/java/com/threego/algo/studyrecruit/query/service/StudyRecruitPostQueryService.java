package com.threego.algo.studyrecruit.query.service;

import com.threego.algo.studyrecruit.query.dto.*;

import java.util.List;

public interface StudyRecruitPostQueryService {
    List<StudyRecruitPostDTO> findStudyRecruitList(StudyRecruitSearchDTO searchDto);

    StudyRecruitDetailDTO findStudyRecruitDetail(Long postId);

    List<StudyRecruitCommentDTO> findStudyRecruitComments(Long postId);

    List<StudyRecruitMemberDTO> findStudyRecruitMembers(Long postId);

    List<StudyRecruitPostDTO> findStudyRecruitListIncludeHidden(StudyRecruitSearchDTO searchDto);

    StudyRecruitDetailDTO findStudyRecruitDetailIncludeHidden(Long postId);

    List<StudyRecruitCommentDTO> findAllStudyRecruitCommentsIncludeHidden(StudyRecruitSearchDTO searchDto);

    List<StudyRecruitCommentDTO> findStudyRecruitCommentsIncludeHidden(Long postId);
}
