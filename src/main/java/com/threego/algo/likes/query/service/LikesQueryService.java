package com.threego.algo.likes.query.service;

import com.threego.algo.likes.command.domain.aggregate.enums.Type;

public interface LikesQueryService {
    boolean existsLikesByMemberIdAndPostIdAndPostType(final int memberId, final int postId, final Type type);
}