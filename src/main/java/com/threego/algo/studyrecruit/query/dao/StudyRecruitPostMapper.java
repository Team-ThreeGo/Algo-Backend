package com.threego.algo.studyrecruit.query.dao;

import com.threego.algo.studyrecruit.query.dto.StudyRecruitDetailDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitMemberDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitPostDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyRecruitPostMapper {

    /* 설명. 스터디 모집글 전체 목록 조회 */
    List<StudyRecruitPostDTO> selectStudyRecruitList(StudyRecruitSearchDTO searchDto);

    /* 설명. 스터디 모집글 상세 조회 */
    StudyRecruitDetailDTO selectStudyRecruitDetail(Long id);

    /* 설명. 스터디 모집 신청자 리스트 조회 */
    List<StudyRecruitMemberDTO> selectStudyRecruitMembers(Long postId);

    /* 설명. 관리자: 숨김 처리된 게시물 리스트 조회 */
    List<StudyRecruitPostDTO> selectStudyRecruitListIncludeHidden(StudyRecruitSearchDTO searchDto);

    /* 설명. 관리자: 숨김 처리된 게시물 상세 조회 */
    StudyRecruitDetailDTO selectStudyRecruitDetailIncludeHidden(Long postId);

}
