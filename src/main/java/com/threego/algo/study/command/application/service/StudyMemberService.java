package com.threego.algo.study.command.application.service;

import org.springframework.http.ResponseEntity;

public interface StudyMemberService {
    /* 설명. 그룹원 강퇴 */
    ResponseEntity<String> kickMember(int studyId, int memberId, int leaderId);

    /* 설명. 그룹장 권한 위임 */
    ResponseEntity<String> delegateLeadership(int studyId, int newLeaderId, int currentLeaderId);
}
