package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dao.StudyMapper;
import com.threego.algo.study.query.dto.StudyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyQueryServiceImpl implements StudyQueryService {

    private final StudyMapper studyMapper;

    /* 설명. 스터디 정보 조회 */
    @Override
    public StudyDTO findStudyById(int studyId) {
        StudyDTO study = studyMapper.selectStudyById(studyId);
        if (study == null) {
            throw new IllegalArgumentException("존재하지 않는 스터디입니다.");
        }
        return study;
    }
}
