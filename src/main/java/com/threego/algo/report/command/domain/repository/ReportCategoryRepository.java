package com.threego.algo.report.command.domain.repository;

import com.threego.algo.report.command.domain.aggregate.ReportCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCategoryRepository extends JpaRepository<ReportCategory, Integer> {
}
