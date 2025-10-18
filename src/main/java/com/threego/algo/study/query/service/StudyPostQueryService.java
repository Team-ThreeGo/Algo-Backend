package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
import com.threego.algo.study.query.dto.StudyPostListResponseDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;

import java.util.List;

public interface StudyPostQueryService {

    StudyPostListResponseDTO findAllStudyPosts(StudyPostSearchDTO searchDto);

    StudyPostDetailDTO findStudyPostDetail(int postId);

    List<StudyCommentDTO> findStudyPostComments(int postId);

    // ========== 관리자용 메소드들 ==========

    List<StudyPostDTO> findAllHiddenStudyPosts(StudyPostSearchDTO searchDto);

    StudyPostDetailDTO findHiddenStudyPostDetail(int postId);

    List<StudyCommentDTO> findAllHiddenStudyComments(StudyPostSearchDTO searchDto);
}
