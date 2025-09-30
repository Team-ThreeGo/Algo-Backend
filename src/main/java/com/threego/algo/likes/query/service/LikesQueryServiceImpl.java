package com.threego.algo.likes.query.service;

import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.likes.query.dao.LikesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikesQueryServiceImpl implements LikesQueryService {
    private final LikesMapper likesMapper;

    @Override
    public boolean existsLikesByMemberIdAndPostIdAndPostType(final int memberId, final int postId, final Type type) {
        return likesMapper.existsLikesByMemberIdAndPostIdAndPostType(memberId, postId, type.name());
    }
}