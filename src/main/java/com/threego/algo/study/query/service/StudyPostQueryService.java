package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;

import java.util.List;

public interface StudyPostQueryService {

    List<StudyPostDTO> findAllStudyPosts(StudyPostSearchDTO searchDto, int memberId);

    StudyPostDetailDTO findStudyPostDetail(int postId, int memberId);

    List<StudyCommentDTO> findStudyPostComments(int postId, int memberId);

    // ========== 관리자용 메소드들 ==========

    List<StudyPostDTO> findAllHiddenStudyPosts(StudyPostSearchDTO searchDto, int adminId);

    StudyPostDetailDTO findHiddenStudyPostDetail(int postId, int adminId);

    List<StudyCommentDTO> findAllHiddenStudyComments(StudyPostSearchDTO searchDto, int adminId);
}
