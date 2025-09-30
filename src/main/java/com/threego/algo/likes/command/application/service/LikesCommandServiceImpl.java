package com.threego.algo.likes.command.application.service;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoPost;
import com.threego.algo.career.command.domain.aggregate.CareerInfoPost;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.likes.command.domain.aggregate.Likes;
import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.likes.command.domain.repository.LikesCommandRepository;
import com.threego.algo.member.command.domain.aggregate.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesCommandServiceImpl implements LikesCommandService {
    private final LikesCommandRepository likesCommandRepository;

    @Transactional
    @Override
    public void createLikes(final Member member, final Object post, final Type type) {
        Likes likes = null;

        switch (type) {
            case ALGO_POST -> {
                final AlgoPost algoPost = (AlgoPost) post;

                likes = new Likes(member, algoPost, null, null);
            }
            case CODING_POST -> {
                final CodingPost codingPst = (CodingPost) post;

                likes = new Likes(member, null, codingPst, null);
            }
            case CAREER_INFO_POST -> {
                final CareerInfoPost careerInfoPost = (CareerInfoPost) post;

                likes = new Likes(member, null, null, careerInfoPost);
            }
        }

        likesCommandRepository.save(likes);
    }
}