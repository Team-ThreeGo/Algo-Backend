package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dao.CodingProblemMapper;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingProblemDetailDTO;
import com.threego.algo.coding.query.dto.CodingProblemSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodingProblemQueryImpl implements CodingProblemQueryService {
    private final CodingProblemMapper codingProblemMapper;

    @Override
    public List<CodingProblemSummaryDTO> findProblemsList(String keyword) {
        CodingPostSearchConditionDTO condition = new CodingPostSearchConditionDTO();
        condition.setKeyword(keyword);
        return codingProblemMapper.selectProblemsList(condition);
    }

    @Override
    public List<CodingProblemSummaryDTO> findProblemsByDifficulty() {
        return codingProblemMapper.selectProblemsByDifficulty();
    }

    @Override
    public CodingProblemDetailDTO findProblemDetail(int problemId) {
        return codingProblemMapper.selectProblemDetail(problemId);
    }

    @Override
    public List<CodingProblemSummaryDTO> findProblemsListForAdmin(String visibility, String keyword) {
        CodingPostSearchConditionDTO condition = new CodingPostSearchConditionDTO();
        condition.setVisibility(visibility);
        condition.setKeyword(keyword);
        return codingProblemMapper.selectAdminProblemsList(condition);
    }

    @Override
    public CodingProblemDetailDTO findProblemDetailForAdmin(int problemId) {
        return codingProblemMapper.selectAdminProblemDetail(problemId);
    }

//    @Override
//    @Transactional
//    public void syncPostCount() {
//        codingProblemMapper.syncPostCount();
//    }
}
