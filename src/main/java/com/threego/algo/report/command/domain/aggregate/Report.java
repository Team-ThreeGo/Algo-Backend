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


    @Column(name = "target_id", nullable = false)
    private int targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_member_id", nullable = false)
    private Member reportedMember;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    public static Report create(
            Member reporter,
            ReportCategory category,
            ReportType type,
            int targetId,
            Member reportedMember,
            String content,
            String createdAt
    ) {
        Report report = new Report();
        report.member = reporter;
        report.category = category;
        report.type = type;
        report.targetId = targetId;
        report.reportedMember = reportedMember;
        report.content = content;
        report.createdAt = createdAt;
        return report;
    }
}

