package com.threego.algo.algorithm.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "algo_post")
@NoArgsConstructor
@Entity
public class AlgoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(nullable = false, length = 1)
    private String visibility;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "comment_count")
    private int commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private AlgoRoadmap algoRoadmap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public AlgoPost(final String title, final String content, final AlgoRoadmap algoRoadmap, final Member member) {
        this.title = title;
        this.content = content;
        this.createdAt = DateTimeUtils.nowDateTime();
        this.visibility = "Y";
        this.likeCount = 0;
        this.commentCount = 0;
        this.algoRoadmap = algoRoadmap;
        this.member = member;
    }

    public void updateVisibility() {
        this.visibility = "N";

        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    public void updateCommentCount(final int commentCount) {
        if (commentCount < 0) {
            this.commentCount = 0;
        } else {
            this.commentCount = commentCount;
        }
    }

    public void updateAlgoPost(final String title, final String content) {
        if (!(title.equals(this.title) && content.equals(this.content))) {
            this.title = title;
            this.content = content;
            this.updatedAt = DateTimeUtils.nowDateTime();
        }
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }
}