package com.threego.algo.report.query.controller;

import com.threego.algo.member.query.dto.AdminMemberDetailResponseDTO;
import com.threego.algo.report.query.dto.ReportDetailResponseDTO;
import com.threego.algo.report.query.service.AdminReportQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "[Admin] Report API", description = "관리자 신고 조회 API")
@RestController
@RequestMapping("/admin")
public class AdminReportQueryController {
    private final AdminReportQueryService reportService;

    @Autowired
    public AdminReportQueryController(AdminReportQueryService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "관리자용 전체 신고 목록 조회",
            description = "관리자는 모든 신고 목록을 조회할 수 있다."
    )
    @GetMapping("/reports")
    public ResponseEntity<List<ReportDetailResponseDTO>> findReportList(){
        return ResponseEntity.ok(reportService.findReportList());
    }

    @Operation(
            summary = "관리자용 특정 회원 신고 목록 조회",
            description = "관리자는 특정 회원의 모든 신고 목록을 조회할 수 있다."
    )
    @GetMapping("/member/{memberId}/reports")
    public ResponseEntity<List<ReportDetailResponseDTO>> findReportListByMemberId(
            @PathVariable int memberId
    ) {
        return ResponseEntity.ok(reportService.findReportListByMemberId(memberId));
    }

    @Operation(
            summary = "관리자용 특정 회원이 당한 신고 목록 조회",
            description = "관리자는 특정 회원이 당한 모든 신고 목록을 조회할 수 있다."
    )
    @GetMapping("/member/{memberId}/reported")
    public ResponseEntity<List<ReportDetailResponseDTO>> findReportedListByMemberId(
            @PathVariable int memberId
    ) {
        return ResponseEntity.ok(reportService.findReportedListByMemberId(memberId));
    }


}
