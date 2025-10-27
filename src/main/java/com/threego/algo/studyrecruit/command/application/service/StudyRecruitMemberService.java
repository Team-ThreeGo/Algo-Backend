package com.threego.algo.studyrecruit.command.application.service;

import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitMemberCreateDTO;
import org.springframework.http.ResponseEntity;

public interface StudyRecruitMemberService {

    /* 설명. 참가 신청 */
    ResponseEntity<String> applyToStudy(int postId, int memberId, StudyRecruitMemberCreateDTO request);

    /* 설명. 참가 신청 취소 */
    ResponseEntity<String> cancelApplication(int joinId, int memberId);

    /* 설명. 참가 신청 승인 */
    ResponseEntity<String> acceptApplication(int joinId, int authorId);

    /* 설명. 참가 신청 거절 */
    ResponseEntity<String> rejectApplication(int joinId, int authorId);
}