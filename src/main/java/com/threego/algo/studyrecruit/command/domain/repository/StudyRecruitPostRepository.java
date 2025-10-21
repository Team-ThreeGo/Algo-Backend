package com.threego.algo.studyrecruit.command.domain.repository;

import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitPost;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRecruitPostRepository extends JpaRepository<StudyRecruitPost, Integer> {


    // 모집글 삭제용 - 작성자 권한 확인 후 soft delete
    Optional<StudyRecruitPost> findByIdAndMemberIdAndVisibility(int id, int memberId, String visibility);

    @Modifying
    @Query("UPDATE StudyRecruitPost s SET s.visibility = 'N', s.updatedAt = :updatedAt WHERE s.id = :id")
    void softDeleteById(@Param("id") int id, @Param("updatedAt") String updatedAt);

    // 모집 마감용 - 작성자 권한 확인 및 상태 변경
    @Modifying
    @Query("UPDATE StudyRecruitPost s SET s.status = :status, s.updatedAt = :updatedAt WHERE s.id = :id")
    void updateStatusToClosed(@Param("id") int id, @Param("status") RecruitStatus status, @Param("updatedAt") String updatedAt);
}
