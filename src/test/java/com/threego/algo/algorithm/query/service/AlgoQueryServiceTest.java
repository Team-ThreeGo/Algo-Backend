package com.threego.algo.algorithm.query.service;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import com.threego.algo.algorithm.query.dao.AlgoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AlgoQueryServiceTest {
    @Mock
    AlgoMapper algoMapper;

    @InjectMocks
    AlgoQueryServiceImpl algoService;

    @Test
    void 전체_알고리즘_학습_로드맵_조회_테스트() {
        // given
        List<AlgoRoadmap> algoRoadmaps = List.of(
                new AlgoRoadmap("자료구조", "자료구조입니다.", 1, "2025-09-02 09:00:00"),
                new AlgoRoadmap("정렬", "정렬입니다.", 2, "2025-09-03 09:00:00"),
                new AlgoRoadmap("DP", "DP입니다.", 3, "2025-09-04 09:00:00")
        );

        Mockito.when(algoMapper.selectAllAlgoRoadmaps()).thenReturn(algoRoadmaps);

        // when
        List<AlgoRoadmap> result = algoService.findAllAlgoRoadmaps();

        // then
        Assertions.assertEquals(3, result.size());
    }
}