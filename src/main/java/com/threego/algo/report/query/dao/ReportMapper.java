package com.threego.algo.report.query.dao;

import com.threego.algo.report.query.dto.ReportDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<ReportDetailResponseDTO> selectAllReportDetails();

    List<ReportDetailResponseDTO> selectReportDetailsById(int memberId);

    List<ReportDetailResponseDTO> selectReportedDetailsById(int memberId);
}
