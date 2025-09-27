package com.threego.algo.coding.query.dao;

import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingProblemSummaryDTO;
import com.threego.algo.coding.query.dto.CodingProblemDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodingProblemMapper {

    // 문제 목록 조회 (전체)
    List<CodingProblemSummaryDTO> selectProblemsList(CodingPostSearchConditionDTO condition);

    // 문제 목록 조회 (난이도/플랫폼 기준 정렬)
    List<CodingProblemSummaryDTO> selectProblemsByDifficulty();

    // 문제 상세 조회
    CodingProblemDetailDTO selectProblemDetail(@Param("problemId") int problemId);

    // 관리자용 문제 전체 목록 조회
    List<CodingProblemSummaryDTO> selectAdminProblemsList(CodingPostSearchConditionDTO condition);

    // 관리자용 문제 상세 조회
    CodingProblemDetailDTO selectAdminProblemDetail(@Param("problemId") int problemId);

    // post_count 동기화
    void syncPostCount();
}
