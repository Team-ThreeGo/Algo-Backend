package com.threego.algo.study.query.dao;

import com.threego.algo.study.query.dto.StudyCommentDTO;
import com.threego.algo.study.query.dto.StudyPostDTO;
import com.threego.algo.study.query.dto.StudyPostDetailDTO;
import com.threego.algo.study.query.dto.StudyPostSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyPostMapper {

    /* 설명. 스터디 게시물 목록 조회 */
    List<StudyPostDTO> selectAllStudyPosts(StudyPostSearchDTO searchDto);

    /* 설명. 스터디 게시물 상세 조회 */
    StudyPostDetailDTO selectStudyPostDetail(Integer postId);

    /* 설명. 스터디 게시물 댓글 조회 */
    List<StudyCommentDTO> selectStudyPostComments(Integer postId);

    /* 설명. 관리자: 숨김 처리된 게시물 목록 조회 */
    List<StudyPostDTO> selectAllHiddenStudyPosts(StudyPostSearchDTO searchDto);

    /* 설명. 관리자: 숨김 처리된 스터디 게시물 상세 조회 */
    StudyPostDetailDTO selectHiddenStudyPostDetail(Integer postId);

    /* 설명. 관리자: 숨김 처리된 스터디 게시물 댓글 조회 */
    List<StudyCommentDTO> selectAllHiddenStudyComments(StudyPostSearchDTO searchDto);
}
