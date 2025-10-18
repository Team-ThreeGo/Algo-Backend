package com.threego.algo.studyrecruit.command.domain.repository;

import com.threego.algo.studyrecruit.command.domain.aggregate.StudyRecruitComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudyRecruitCommentRepository extends JpaRepository<StudyRecruitComment, Integer> {

    // 댓글 삭제용 - 작성자 권한 확인 후 soft delete
    Optional<StudyRecruitComment> findByIdAndMemberIdAndVisibility(int id, int memberId, String visibility);

    @Modifying
    @Query("UPDATE StudyRecruitComment c SET c.visibility = 'N', c.updatedAt = :updatedAt WHERE c.id = :id")
    void softDeleteById(@Param("id") int id, @Param("updatedAt") String updatedAt);

    Optional<StudyRecruitComment> findByIdAndVisibility(Integer parentId, String visibility);
}