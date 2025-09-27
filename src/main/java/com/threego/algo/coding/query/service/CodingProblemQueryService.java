package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dto.CodingProblemDetailDTO;
import com.threego.algo.coding.query.dto.CodingProblemSummaryDTO;

import java.util.List;

public interface CodingProblemQueryService {
    // 문제 목록 조회 (전체)
    List<CodingProblemSummaryDTO> findProblemsList(String keyword);

    // 문제 목록 조회 (난이도/플랫폼 기준 정렬)
    List<CodingProblemSummaryDTO> findProblemsByDifficulty();

    // 문제 상세 조회
    CodingProblemDetailDTO findProblemDetail(int problemId);

    // 관리자용 문제 전체 목록 조회
    List<CodingProblemSummaryDTO> findProblemsListForAdmin(String visibility, String keyword);

    // 관리자용 문제 상세 조회
    CodingProblemDetailDTO findProblemDetailForAdmin(int problemId);

    // post_count 동기화
//    void syncPostCount();
}
