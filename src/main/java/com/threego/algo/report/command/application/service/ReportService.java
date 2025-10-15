package com.threego.algo.report.command.application.service;

import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.report.command.application.dto.ReportRequest;

public interface ReportService {
    void createReport(ReportRequest request);
    void changeVisibility(Member member);
}
