package com.threego.algo.study.query.dao;

import com.threego.algo.study.query.dto.StudyRoadmapDTO;
import com.threego.algo.study.query.dto.StudyRoadmapDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyRoadmapMapper {

    /* 설명. 스터디 로드맵 목록 조회 */
    List<StudyRoadmapDTO> selectAllStudyRoadmap(Integer studyId);

    /* 설명. 스터디 로드맵 상세 조회 */
    StudyRoadmapDetailDTO selectStudyRoadmapDetail(Integer roadmapId);

}