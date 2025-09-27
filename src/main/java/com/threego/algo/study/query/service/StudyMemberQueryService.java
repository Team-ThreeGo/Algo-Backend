package com.threego.algo.study.query.service;

import com.threego.algo.study.query.dto.StudyMemberDTO;

import java.util.List;


public interface StudyMemberQueryService {

    List<StudyMemberDTO> findAllStudyMember(Integer studyId);

}
