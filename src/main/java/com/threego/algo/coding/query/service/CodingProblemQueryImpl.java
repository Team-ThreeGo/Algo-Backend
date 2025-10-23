package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dao.CodingPostMapper;
import com.threego.algo.coding.query.dao.CodingProblemMapper;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import com.threego.algo.coding.query.dto.CodingProblemDetailDTO;
import com.threego.algo.coding.query.dto.CodingProblemSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodingProblemQueryImpl implements CodingProblemQueryService {
    private final CodingProblemMapper codingProblemMapper;
    private final CodingPostMapper codingPostMapper;

    @Override
    public List<CodingProblemSummaryDTO> findProblemsList(String keyword, String platform, String difficulty, String sortBy, int page, int size) {
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .keyword(keyword)
                .platform(platform)
                .difficulty(difficulty)
                .sortBy(sortBy)
                .page(page)
                .size(size)
                .build();
        return codingProblemMapper.selectProblemsList(condition);
    }

    @Override
    public List<CodingProblemSummaryDTO> findProblemsByDifficulty() {
        return codingProblemMapper.selectProblemsByDifficulty();
    }

    @Override
    public List<CodingPostSummaryDTO> findPostListByProblemId(int problemId, String keyword, int page, int size) {
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .problemId(problemId)
                .keyword(keyword)
                .page(page)
                .size(size)
                .build();
        return codingPostMapper.selectPostListByProblemId(condition);
    }
    
    @Override
    public CodingProblemDetailDTO findProblemDetail(int problemId) {
        return codingProblemMapper.selectProblemDetail(problemId);
    }

    @Override
    public List<CodingProblemSummaryDTO> findProblemsListForAdmin(String visibility, String keyword, int page, int size) {
        CodingPostSearchConditionDTO condition = CodingPostSearchConditionDTO.builder()
                .visibility(visibility)
                .keyword(keyword)
                .page(page)
                .size(size)
                .build();
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
