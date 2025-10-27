package com.threego.algo.study.query.dao;

import com.threego.algo.study.query.dto.StudyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyMapper {

    /* 설명. 스터디 정보 조회 */
    StudyDTO selectStudyById(int studyId);
}
