package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dto.StudyDTO;

public interface StudyQueryService {

    /* 설명. 스터디 정보 조회 */
    StudyDTO findStudyById(int studyId);
}
