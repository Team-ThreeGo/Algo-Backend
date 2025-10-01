package com.threego.algo.report.command.application.controller;

import com.threego.algo.report.command.application.dto.ReportRequest;
import com.threego.algo.report.command.application.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report - Report Command", description = "회원용 신고 API(Command)")
@RestController
@RequestMapping("/report")
public class ReportComandController {
    private final ReportService service;

    @Autowired
    public ReportComandController(ReportService reportService) {
        this.service = reportService;
    }

    @Operation(
            summary = "게시물 및 댓글 신고",
            description = "회원이 게시물 및 댓글을 신고합니다."
    )
    @PostMapping
    public ResponseEntity<Integer> createReport(
            @RequestBody ReportRequest request
    ) {
        service.createReport(request);
        return ResponseEntity.ok().build();
    }





}
