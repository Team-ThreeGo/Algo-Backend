package com.threego.algo.studyrecruit.query.service;

import com.threego.algo.studyrecruit.query.dto.*;

import java.util.List;

public interface StudyRecruitPostQueryService {
    StudyRecruitPostListResponseDTO findStudyRecruitList(StudyRecruitSearchDTO searchDto);

    StudyRecruitDetailDTO findStudyRecruitDetail(int postId);

    List<StudyRecruitCommentDTO> findStudyRecruitComments(int postId);

    List<StudyRecruitMemberDTO> findStudyRecruitMembers(int postId);

    List<StudyRecruitPostDTO> findStudyRecruitListIncludeHidden(StudyRecruitSearchDTO searchDto);

    StudyRecruitDetailDTO findStudyRecruitDetailIncludeHidden(int postId);

    List<StudyRecruitCommentDTO> findAllStudyRecruitCommentsIncludeHidden(StudyRecruitSearchDTO searchDto);

    List<StudyRecruitCommentDTO> findStudyRecruitCommentsIncludeHidden(int postId);
}
