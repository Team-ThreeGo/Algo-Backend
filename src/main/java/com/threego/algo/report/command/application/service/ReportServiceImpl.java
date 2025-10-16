package com.threego.algo.report.command.application.service;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.enums.Status;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.report.command.application.dto.ReportRequest;
import com.threego.algo.report.command.domain.aggregate.Report;
import com.threego.algo.report.command.domain.aggregate.ReportCategory;
import com.threego.algo.report.command.domain.aggregate.ReportType;
import com.threego.algo.report.command.domain.repository.ReportCategoryRepository;
import com.threego.algo.report.command.domain.repository.ReportRepository;
import com.threego.algo.report.command.domain.repository.ReportTypeRepository;
import com.threego.algo.report.query.dao.ReportMapper;
import com.threego.algo.report.query.dto.ReportedMemberResponseDTO;
import com.threego.algo.report.query.service.AdminReportQueryService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportCategoryRepository categoryRepository;
    private final ReportTypeRepository typeRepository;
    private final MemberRepository memberRepository;
    private final AdminReportQueryService reportService;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public void createReport(ReportRequest request, int memberId) {
        ReportCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        ReportType type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신고 타입입니다."));

        Member reporter = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신고자입니다."));

        Integer reportedMemberId = reportService.findReportedMemberId(
                request.getCategoryId(),
                request.getTargetId()
        );
        if (reportedMemberId == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        Member reportedMember = memberRepository.findById(reportedMemberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 중복 신고 방지
        boolean exists = reportRepository.existsByMemberAndCategoryAndTargetIdAndTypeAndReportedMember(
                reporter,
                category,
                request.getTargetId(),
                type,
                reportedMember
        );
        if (exists) {
            throw new IllegalArgumentException("이미 신고한 게시물/댓글입니다.");
        }

        Report report = Report.create(
                reporter,
                category,
                type,
                request.getTargetId(),
                reportedMember,
                request.getContent(),
                DateTimeUtils.nowDateTime()
        );

        reportedMember.increaseReportCount();

        // 20번 이상 신고된 회원 Block 처리
        if(reportedMember.getReportedCount() >= 20){
            reportedMember.setStatus(Status.BLOCKED);

            // Block된 회원이 작성한 게시물 및 댓글 비공개
            changeVisibility(reportedMember);
        }

        reportRepository.save(report);
    }

    @Override
    public void changeVisibility(Member member) {
        String[] tables = {
                "Algo_Comment", "Career_Info_Comment", "Career_Info_Post",
                "Coding_Post", "Coding_Comment", "Study_Comment",
                "Study_Post", "Study_Recruit_Comment", "Study_Recruit_Post"
        };

        for (String table : tables) {
            entityManager.createNativeQuery("UPDATE " + table + " SET visibility = 'N' WHERE member_id = :memberId")
                    .setParameter("memberId", member.getId())
                    .executeUpdate();
        }
    }


}

