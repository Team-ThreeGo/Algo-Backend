package com.threego.algo.report.query.service;

import com.threego.algo.report.query.dao.ReportMapper;
import com.threego.algo.report.query.dto.ReportContentResponseDTO;
import com.threego.algo.report.query.dto.ReportDetailResponseDTO;
import com.threego.algo.report.query.dto.ReportedMemberResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReportQueryServiceImpl implements AdminReportQueryService {
    private final ReportMapper reportMapper;

    public AdminReportQueryServiceImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    @Override
    public List<ReportDetailResponseDTO> findReportList() {
        return reportMapper.selectAllReportDetails();
    }

    @Override
    public List<ReportDetailResponseDTO> findReportListByMemberId(int memberId) {
        List<ReportDetailResponseDTO> dto = reportMapper.selectReportDetailsById(memberId);
        return dto;
    }

    @Override
    public List<ReportDetailResponseDTO> findReportedListByMemberId(int memberId) {
        List<ReportDetailResponseDTO> dto = reportMapper.selectReportedDetailsById(memberId);
        return dto;
    }

    @Override
    public List<ReportContentResponseDTO> findDetailByReportId(int reportId) {
        List<ReportContentResponseDTO> dto = reportMapper.selectReportContentDetailsByReportId(reportId);
        return dto;
    }

    @Override
    public Integer findReportedMemberId(int categoryId, int targetId) {
        Integer dto = reportMapper.selectReportedMember(categoryId, targetId);
        return dto;
    }
}
