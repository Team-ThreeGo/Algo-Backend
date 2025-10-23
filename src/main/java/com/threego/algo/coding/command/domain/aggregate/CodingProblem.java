package com.threego.algo.coding.command.domain.aggregate;

import com.threego.algo.coding.command.domain.aggregate.enums.Platform;
import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Coding_Problem")
public class CodingProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;   // ENUM('BOJ','PGS','ETC')

    @Column(nullable = false, length = 50)
    private String difficulty;

    @Lob
    private String input;

    @Lob
    private String output;

    @Column(name = "problem_url", nullable = false, length = 255)
    private String problemUrl;

    @Lob
    private String constraints;

    @Column(name = "post_count", nullable = false)
    private int postCount = 0;

    @Column(nullable = false, length = 1)
    private String visibility = "Y";

    @Column(name = "created_at", nullable = false, length = 20)
    private String createdAt;

    @Builder
    public CodingProblem(Member member, Platform platform, String title, String problemUrl,
                         String difficulty, String content, String input,
                         String output, String constraints) {
        this.member = member;
        this.platform = platform;
        this.title = title;
        this.problemUrl = problemUrl;
        this.difficulty = difficulty;
        this.content = content;
        this.input = input;
        this.output = output;
        this.constraints = constraints;
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    public void update(String title, String problemUrl, String difficulty, String content,
                       String input, String output, String constraints) {
        this.title = title;
        this.problemUrl = problemUrl;
        this.difficulty = difficulty;
        this.content = content;
        this.input = input;
        this.output = output;
        this.constraints = constraints;
    }

    public void delete() {
        this.visibility = "N";
    }


    // 양방향 매핑
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodingPost> posts = new ArrayList<>();

    // postCount 동기화 메서드
    public void syncPostCount() {
        this.postCount = (int) posts.stream()
                .filter(post -> "Y".equals(post.getVisibility()))  // 공개글만 카운트
                .count();
    }

    public void setDifficulty(String difficulty) {
    }

    public void setContent(String content) {
    }

    public void setInput(String input) {
    }

    public void setOutput(String output) {
    }

    public void setConstraints(String constraints) {
    }
}
