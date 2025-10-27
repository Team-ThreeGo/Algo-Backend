package com.threego.algo.algorithm.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "algo_comment")
@NoArgsConstructor
@Entity
public class AlgoComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(nullable = false, length = 1)
    private String visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private AlgoPost algoPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AlgoComment parent;

    public AlgoComment(final String content, final Member member, final AlgoPost algoPost, final AlgoComment parent) {
        this.content = content;
        this.createdAt = DateTimeUtils.nowDateTime();
        this.member = member;
        this.algoPost = algoPost;
        this.parent = parent;
        this.visibility = "Y";
    }

    public void updateContent(final String content) {
        if (!this.content.equals(content)) {
            this.content = content;
            this.updatedAt = DateTimeUtils.nowDateTime();
        }
    }

    public void updateVisibility() {
        this.visibility = "N";
        this.updatedAt = DateTimeUtils.nowDateTime();
    }
}