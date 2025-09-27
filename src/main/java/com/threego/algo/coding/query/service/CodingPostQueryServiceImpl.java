package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dao.CodingPostMapper;
import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodingPostQueryServiceImpl implements CodingPostQueryService {

    private final CodingPostMapper codingPostMapper;

    // ---------------- 회원용 ---------------- //

    @Override
    public List<CodingPostSummaryDTO> findPostList(String keyword) {
        CodingPostSearchConditionDTO condition = new CodingPostSearchConditionDTO();
        condition.setKeyword(keyword);
        return codingPostMapper.selectPostList(condition);
    }

    @Override
    public List<CodingPostSummaryDTO> getPostsByProblemId(String keyword) {
        return List.of();
    }

    @Override
    public List<CodingPostSummaryDTO> findPostByProblemIdOrderByLike(int problemId, int likeCount) {
        return codingPostMapper.selectPostByProblemIdOrderByLike(problemId);
    }

    @Override
    public CodingPostDetailDTO findPostDetail(int postId) {
        return codingPostMapper.selectPostDetail(postId);
    }

    @Override
    public List<CodingPostCommentDTO> findCommentsByPostId(int postId) {
        return codingPostMapper.selectCommentsByPostId(postId);
    }

    // ---------------- 관리자용 ---------------- //

    @Override
    public List<CodingPostSummaryDTO> findPostListForAdmin(String visibility, String keyword) {
        CodingPostSearchConditionDTO condition = new CodingPostSearchConditionDTO();
        condition.setVisibility(visibility);
        condition.setKeyword(keyword);
        return codingPostMapper.selectAdminPostList(condition);
    }

    @Override
    public CodingPostDetailDTO findPostDetailForAdmin(int postId) {
        return codingPostMapper.selectAdminPostDetail(postId);
    }

    @Override
    public List<CodingPostCommentDTO> findCommentsForAdmin() {
        return codingPostMapper.selectAdminComments();
    }
}
