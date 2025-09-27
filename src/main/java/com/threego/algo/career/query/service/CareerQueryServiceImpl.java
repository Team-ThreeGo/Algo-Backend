package com.threego.algo.career.query.service;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.career.query.dao.CareerInfoMapper;
import com.threego.algo.career.query.dto.CommentResponseDto;
import com.threego.algo.career.query.dto.PostDetailResponseDto;
import com.threego.algo.career.query.dto.PostSummaryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CareerQueryServiceImpl implements CareerQueryService {
    private final CareerInfoMapper careerInfoMapper;

    @Autowired
    public CareerQueryServiceImpl(CareerInfoMapper careerInfoMapper) {
        this.careerInfoMapper = careerInfoMapper;
    }

    @Override
    public List<PostSummaryResponseDto> findPostList(String visibility, Status status, String keyword) {
        return careerInfoMapper.selectPostList(visibility, status, keyword);
    }

    @Override
    public PostDetailResponseDto findPostForMember(int postId) {
        PostDetailResponseDto dto = careerInfoMapper.selectPost(postId, "Y");
        dto.setImageUrl(null);  // 회원에게는 이미지 숨김
        return dto;
    }

    @Override
    public PostDetailResponseDto findPostForAdmin(int postId) {
        return careerInfoMapper.selectPost(postId, null);
    }

    @Override
    public List<CommentResponseDto> findCommentsByPostId(int postId) {
        List<CommentResponseDto> flatComments
                = careerInfoMapper.selectCommentsByPostId(postId);

        // 트리 구조로 변환
        Map<Integer, CommentResponseDto> map = new HashMap<>();
        List<CommentResponseDto> roots = new ArrayList<>();

        for(CommentResponseDto c : flatComments) {
            c.setChildren(new ArrayList<>());
            map.put(c.getCommentId(), c);
        }

        for(CommentResponseDto c : flatComments) {
            if(c.getParentId() == null) {
                roots.add(c);
            } else {
                CommentResponseDto parent = map.get(c.getParentId());
                if (parent != null) {
                    parent.getChildren().add(c);
                }
            }
        }

        return roots;
    }

    @Override
    public List<PostDetailResponseDto> findCommentsForAdmin() {
        return careerInfoMapper.selectComments();
    }

}
