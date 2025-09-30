package com.threego.algo.likes.command.application.service;

import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.member.command.domain.aggregate.Member;

public interface LikesCommandService {
    void createLikes(final Member member, final Object post, final Type type);
}