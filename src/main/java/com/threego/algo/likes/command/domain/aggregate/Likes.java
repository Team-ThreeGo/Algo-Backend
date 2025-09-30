package com.threego.algo.likes.command.domain.aggregate;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoPost;
import com.threego.algo.career.command.domain.aggregate.CareerInfoPost;
import com.threego.algo.coding.command.domain.aggregate.CodingPost;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "likes")
@NoArgsConstructor
@Entity
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algo_post_id")
    private AlgoPost algoPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coding_post_id")
    private CodingPost codingPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_info_post_id")
    private CareerInfoPost careerInfoPost;

    public Likes(final Member member, final AlgoPost algoPost, final CodingPost codingPost,
                 final CareerInfoPost careerInfoPost) {
        this.member = member;
        this.algoPost = algoPost;
        this.codingPost = codingPost;
        this.careerInfoPost = careerInfoPost;
    }
}