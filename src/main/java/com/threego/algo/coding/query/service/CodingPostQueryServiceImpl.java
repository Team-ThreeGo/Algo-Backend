package com.threego.algo.coding.query.service;

import com.threego.algo.coding.query.dao.CodingPostMapper;
import com.threego.algo.coding.query.dto.CodingPostCommentDTO;
import com.threego.algo.coding.query.dto.CodingPostDetailDTO;
import com.threego.algo.coding.query.dto.CodingPostSearchConditionDTO;
import com.threego.algo.coding.query.dto.CodingPostSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public CodingPostDetailDTO findPostDetail(int postId) {
        return codingPostMapper.selectPostDetail(postId);
    }


    @Override
    public List<CodingPostCommentDTO> findCommentsByPostId(int postId) {
        // 평면 구조 가져오기
        List<CodingPostCommentDTO> comments = codingPostMapper.selectCommentsByPostId(postId);

        // ID -> Comment 매핑
        Map<Integer, CodingPostCommentDTO> map = comments.stream()
                .collect(Collectors.toMap(CodingPostCommentDTO::getCommentId, c -> c));

        // 결과 트리
        List<CodingPostCommentDTO> result = new ArrayList<>();

        for (CodingPostCommentDTO comment : comments) {
            if (comment.getParentId() == null) {
                // 최상위 댓글
                result.add(comment);
            } else {
                // 부모 댓글 찾아 children에 추가
                CodingPostCommentDTO parent = map.get(comment.getParentId());
                if (parent != null) {
                    parent.getChildren().add(comment);
                }
            }
        }

        return result;
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
