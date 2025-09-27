package com.threego.algo.coding.command.domain.aggregate;

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

}
