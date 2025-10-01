package com.threego.algo.report.query.dao;

import com.threego.algo.report.query.dto.ReportContentResponseDTO;
import com.threego.algo.report.query.dto.ReportDetailResponseDTO;
import com.threego.algo.report.query.dto.ReportedMemberResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<ReportDetailResponseDTO> selectAllReportDetails();

    List<ReportDetailResponseDTO> selectReportDetailsById(int memberId);

    List<ReportDetailResponseDTO> selectReportedDetailsById(int memberId);

    List<ReportContentResponseDTO> selectReportContentDetailsByReportId(int reportId);

    Integer selectReportedMember(int categoryId, int targetId);
}
