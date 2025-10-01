package com.threego.algo.report.command.domain.repository;

import com.threego.algo.report.command.domain.aggregate.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportTypeRepository extends JpaRepository<ReportType, Integer> {
}
