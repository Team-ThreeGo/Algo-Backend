package com.threego.algo.report.command.domain.aggregate;

import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table(name = "Report")
@NoArgsConstructor
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ReportCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ReportType type;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column
    private String content;

    @Column(nullable = false)
    private int targetId;

    @Column(nullable = false)
    private int reportedMemberId;
}
