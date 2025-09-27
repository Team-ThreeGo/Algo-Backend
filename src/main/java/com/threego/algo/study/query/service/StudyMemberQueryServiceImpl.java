package com.threego.algo.study.query.service;


import com.threego.algo.study.query.dao.StudyMemberMapper;
import com.threego.algo.study.query.dto.StudyMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyMemberQueryServiceImpl implements StudyMemberQueryService {
    private final StudyMemberMapper studyMemberMapper;

    @Override
    public List<StudyMemberDTO> findAllStudyMember(Integer studyId) {
        return studyMemberMapper.selectAllStudyMember(studyId);
    }
}
