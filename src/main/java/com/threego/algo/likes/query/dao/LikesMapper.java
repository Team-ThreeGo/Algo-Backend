package com.threego.algo.likes.query.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikesMapper {
    boolean existsLikesByMemberIdAndPostIdAndPostType(@Param("memberId") final int memberId,
                                                      @Param("postId") final int postId,
                                                      @Param("type") String type);
}