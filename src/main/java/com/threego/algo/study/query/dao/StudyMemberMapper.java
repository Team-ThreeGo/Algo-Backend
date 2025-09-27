package com.threego.algo.study.query.dao;

import com.threego.algo.study.query.dto.StudyMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyMemberMapper {
    /* 설명. 스터디 멤버 목록 조회 */
    List<StudyMemberDTO> selectAllStudyMember(Integer studyId);

}
