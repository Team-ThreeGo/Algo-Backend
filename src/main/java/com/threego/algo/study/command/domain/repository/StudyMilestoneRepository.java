package com.threego.algo.study.command.domain.repository;

import com.threego.algo.study.command.domain.aggregate.StudyMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyMilestoneRepository extends JpaRepository<StudyMilestone, Integer> {

    /* 설명. 하위 마일스톤 존재 여부 확인 */
    boolean existsByRoadmapId(int roadmapId);
}