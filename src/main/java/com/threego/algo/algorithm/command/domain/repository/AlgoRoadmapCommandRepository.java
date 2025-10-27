package com.threego.algo.algorithm.command.domain.repository;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlgoRoadmapCommandRepository extends JpaRepository<AlgoRoadmap, Integer> {
    boolean existsByTitle(final String title);

    @Query("select max(a.order) from AlgoRoadmap a")
    Integer findMaxOrder();
}