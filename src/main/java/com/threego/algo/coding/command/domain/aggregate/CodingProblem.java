package com.threego.algo.coding.command.domain.aggregate;

import com.threego.algo.coding.command.domain.aggregate.enums.Platform;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Member memberId;

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

}
