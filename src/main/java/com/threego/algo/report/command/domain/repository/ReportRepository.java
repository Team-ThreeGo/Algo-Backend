package com.threego.algo.report.command.domain.repository;

import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.report.command.domain.aggregate.Report;
import com.threego.algo.report.command.domain.aggregate.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    boolean existsByMemberAndTargetIdAndTypeAndReportedMember(
            Member member,
            int targetId,
            ReportType type,
            Member reportedMember
    );
}
