package com.threego.algo.algorithm.query.service;

import com.threego.algo.algorithm.command.application.service.AlgoCommandServiceImpl;
import com.threego.algo.algorithm.command.domain.aggregate.AlgoPost;
import com.threego.algo.algorithm.command.domain.aggregate.AlgoRoadmap;
import com.threego.algo.algorithm.command.domain.repository.*;
import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.likes.command.application.service.LikesCommandService;
import com.threego.algo.likes.command.domain.aggregate.enums.Type;
import com.threego.algo.likes.query.service.LikesQueryService;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRank;
import com.threego.algo.member.command.domain.aggregate.enums.RankName;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlgoQueryServiceTest {
    @Mock
    private AlgoPostCommandRepository algoPostCommandRepository;

    @Mock
    private MemberCommandRepository memberCommandRepository;

    @Mock
    private LikesQueryService likesQueryService;

    @Mock
    private LikesCommandService likesCommandService;

    @InjectMocks
    private AlgoCommandServiceImpl algoCommandServiceImpl;

    Member member;

    Member admin;

    AlgoPost algoPost;

    @BeforeEach
    void setUp() {
        final MemberRank memberRank = createMemberRank("코뉴비", 20);

        member = createMember("algo@algo.co.kr", "pass01", "코알고", memberRank);

        admin = createMember("admin.algo.co.kr", "pass02", "관리자", memberRank);

        final AlgoRoadmap algoRoadmap = createAlgoRoadmap("정렬", "버블 정렬부터 퀵 정렬까지 배워봅시다.", 1);

        algoPost = createAlgoPost("버블 정렬", "버블 정렬을 배워봅시다.", algoRoadmap, admin);
    }

    Member createMember(String email, String password, String nickname, MemberRank memberRank) {
        return new Member(email, password, nickname, memberRank, DateTimeUtils.nowDateTime());
    }

    MemberRank createMemberRank(String name, int minPoint) {
        MemberRank memberRank = new MemberRank();

        memberRank.setName(RankName.valueOf(name));
        memberRank.setMinPoint(minPoint);
        memberRank.setImageUrl("https://fastly.picsum.photos/id/1001/200/300.jpg?hmac=nQhEVl6C7qyfiRmcIe41BohR4WBcN1yhONnlCJryahU");

        return memberRank;
    }

    AlgoRoadmap createAlgoRoadmap(final String title, final String description, final int order) {
        return new AlgoRoadmap(title, description, order);
    }

    AlgoPost createAlgoPost(final String title, final String content, final AlgoRoadmap algoRoadmap, final Member member) {
        return new AlgoPost(title, content, algoRoadmap, member);
    }

    @DisplayName("알고리즘 학습 게시물 추천 성공")
    @Test
    void AlgoPostLikesSuccessTest() {
        // given
        when(memberCommandRepository.findById(any())).thenReturn(Optional.of(member));

        when(algoPostCommandRepository.findById(any())).thenReturn(Optional.of(algoPost));

        when(likesQueryService.existsLikesByMemberIdAndPostIdAndPostType(member.getId(), algoPost.getId(), Type.ALGO_POST))
                .thenReturn(false);

        doNothing().when(likesCommandService).createLikes(member, algoPost, Type.ALGO_POST);

        // when
        int likeCount = algoPost.getLikeCount();

        algoCommandServiceImpl.createAlgoPostLikes(member.getId(), algoPost.getId());

        // then
        Assertions.assertEquals(likeCount + 1, algoPost.getLikeCount());
    }
}