package com.threego.algo.coding.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Coding_Post")
public class CodingPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private CodingProblem problem;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    private String content;

    @Column(nullable = false, length = 1)
    private String visibility = "Y";

    @Column(name = "ai_big_o", length = 100)
    private String aiBigO;

    @Column(name = "ai_good", length = 255)
    private String aiGood;

    @Column(name = "ai_bad", length = 255)
    private String aiBad;

    @Column(name = "ai_plan", length = 255)
    private String aiPlan;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "created_at", nullable = false, length = 20)
    private String createdAt;

    @Column(name = "updated_at", length = 20)
    private String updatedAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CodingPostImage> images = new ArrayList<>();

    public CodingPost(Member memberId, CodingProblem problem, String title, String content) {
        this.memberId = memberId;
        this.problem = problem;
        this.title = title;
        this.content = content;
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    public static CodingPost create(Member memberId, CodingProblem problemId, String title, String content) {
        return new CodingPost(memberId, problemId, title, content);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    public void delete() {
        this.visibility = "N";
    }

    public void increaseCommentCount() {
        this.commentCount += 1;
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) this.commentCount -= 1;
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }
}
