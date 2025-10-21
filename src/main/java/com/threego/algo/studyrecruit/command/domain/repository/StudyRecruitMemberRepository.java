package com.threego.algo.studyrecruit.command.domain.repository;

import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyRecruitMemberRepository extends JpaRepository<StudyRecruitMember, Integer> {

    // 중복 신청 방지 체크
    boolean existsByStudyRecruitPostIdAndMemberId(int postId, int memberId);

    // 신청 취소용 - 신청자 본인 확인
    Optional<StudyRecruitMember> findByIdAndMemberId(int applicationId, int memberId);

    // 신청 승인/거절용 - 모집글 작성자 권한 확인
    @Query("SELECT a FROM StudyRecruitMember a WHERE a.id = :applicationId AND a.studyRecruitPost.member.id = :authorId")
    Optional<StudyRecruitMember> findByIdAndAuthor(@Param("applicationId") int applicationId, @Param("authorId") int authorId);
}
