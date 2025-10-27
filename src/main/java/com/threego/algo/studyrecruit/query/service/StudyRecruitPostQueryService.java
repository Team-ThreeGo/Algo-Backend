package com.threego.algo.studyrecruit.query.service;

import com.threego.algo.studyrecruit.query.dto.*;

import java.util.List;

public interface StudyRecruitPostQueryService {
    List<StudyRecruitPostDTO> findStudyRecruitList(StudyRecruitSearchDTO searchDto);

    StudyRecruitDetailDTO findStudyRecruitDetail(int postId);

    List<StudyRecruitCommentDTO> findStudyRecruitComments(int postId);

    List<StudyRecruitMemberDTO> findStudyRecruitMembers(int postId);

    List<MyStudyDTO> findMyApprovedStudies(int memberId);

    List<StudyRecruitPostDTO> findStudyRecruitListIncludeHidden(StudyRecruitSearchDTO searchDto);

    StudyRecruitDetailDTO findStudyRecruitDetailIncludeHidden(int postId);

    List<StudyRecruitCommentDTO> findAllStudyRecruitCommentsIncludeHidden(StudyRecruitSearchDTO searchDto);

    List<StudyRecruitCommentDTO> findStudyRecruitCommentsIncludeHidden(int postId);

    ApplicantStatusDTO findApplicantStatus(int postId, int memberId);
}
