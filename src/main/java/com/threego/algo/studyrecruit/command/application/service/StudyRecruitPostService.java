package com.threego.algo.studyrecruit.command.application.service;

import com.threego.algo.studyrecruit.command.application.dto.create.StudyRecruitPostCreateDTO;
import com.threego.algo.studyrecruit.command.application.dto.update.StudyRecruitPostUpdateDTO;
import com.threego.algo.studyrecruit.query.dto.StudyRecruitMemberDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface StudyRecruitPostService {

    /* 설명. 모집글 등록 */
    void createPost(int memberId, StudyRecruitPostCreateDTO request);

    /* 설명. 모집글 수정 */
    void updatePost(int postId, int memberId, StudyRecruitPostUpdateDTO request);

    /* 설명. 모집글 삭제 */
    void deletePost(int postId, int memberId);

    /* 설명. 모집 마감 */
    void closeRecruitment(int postId, int memberId);

    /* 설명. 모집 신청자 목록 조회 (Study 서비스에서 사용) */
    List<StudyRecruitMemberDTO> findStudyRecruitMembers(@NotNull(message = "모집글 ID는 필수입니다.") int recruitPostId);

    /* 설명. 관리자 권한으로 모집 게시물 소프트 딜리트 */
    void adminDeletePost(int postId, int adminId);
}