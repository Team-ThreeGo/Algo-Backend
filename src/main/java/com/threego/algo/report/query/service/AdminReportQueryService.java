package com.threego.algo.report.query.service;

import com.threego.algo.report.query.dto.ReportDetailResponseDTO;

import java.util.List;

public interface AdminReportQueryService {
    List<ReportDetailResponseDTO> findReportList();

    List<ReportDetailResponseDTO> findReportListByMemberId(int memberId);

    List<ReportDetailResponseDTO> findReportedListByMemberId(int memberId);
}
