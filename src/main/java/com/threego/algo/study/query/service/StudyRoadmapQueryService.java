package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dto.StudyRoadmapDTO;
import com.threego.algo.study.query.dto.StudyRoadmapDetailDTO;

import java.util.List;

public interface StudyRoadmapQueryService {

    List<StudyRoadmapDTO> findAllStudyRoadmap(Integer studyId);

    StudyRoadmapDetailDTO findStudyRoadmapDetail(Integer roadmapId);
}
