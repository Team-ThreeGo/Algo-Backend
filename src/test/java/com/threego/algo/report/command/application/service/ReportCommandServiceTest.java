package com.threego.algo.report.command.application.service;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRank;
import com.threego.algo.member.command.domain.aggregate.enums.RankName;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.report.command.application.dto.ReportRequest;
import com.threego.algo.report.command.domain.aggregate.ReportCategory;
import com.threego.algo.report.command.domain.aggregate.ReportType;
import com.threego.algo.report.command.domain.repository.ReportCategoryRepository;
import com.threego.algo.report.command.domain.repository.ReportRepository;
import com.threego.algo.report.command.domain.repository.ReportTypeRepository;
import com.threego.algo.report.query.service.AdminReportQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReportCommandService 단위 테스트")
class ReportCommandServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ReportCategoryRepository categoryRepository;

    @Mock
    private ReportTypeRepository typeRepository;

    @Mock
    private AdminReportQueryService reportService;

    @InjectMocks
    private ReportServiceImpl reportServiceImpl;

    Member reporter;
    Member reportedMember;
    ReportCategory category;
    ReportType type;

    @BeforeEach
    void setUp() {
        category = new ReportCategory(1, "Study_Recruit_Post");
        type = new ReportType(1, "명예훼손");

        MemberRank rank = createMemberRank("코알못", 0);
        reporter = createMember("reporter@test.com", "pass", "reporter", rank);
        reporter.setId(1);

        reportedMember = createMember("badguy@test.com", "pass", "badguy", rank);
        reportedMember.setId(2);
    }

    @DisplayName("댓글 중복 신고")
    @Test
    void reportComment_Failure() {

        ReportRequest request = new ReportRequest();
        request.setCategoryId(category.getId());
        request.setTypeId(type.getId());
        request.setMemberId(reporter.getId());
        request.setTargetId(10);

        given(memberRepository.findById(reporter.getId())).willReturn(Optional.of(reporter));
        given(memberRepository.findById(reportedMember.getId())).willReturn(Optional.of(reportedMember));

        given(categoryRepository.findById(category.getId())).willReturn(Optional.of(category));
        given(typeRepository.findById(type.getId())).willReturn(Optional.of(type));

        given(reportService.findReportedMemberId(category.getId(), request.getTargetId()))
                .willReturn(reportedMember.getId());

        given(reportRepository.existsByMemberAndTargetIdAndTypeAndReportedMember(
                any(Member.class),
                anyInt(),
                any(ReportType.class),
                any(Member.class))
        ).willReturn(true);

        assertThatThrownBy(() -> reportServiceImpl.createReport(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 신고한 게시물/댓글입니다.");

    }

    @DisplayName("신고 성공 시 피신고자의 reportCount 1 증가")
    @Test
    void reportMember_Success() {
        ReportRequest request = new ReportRequest();
        request.setCategoryId(category.getId());
        request.setTypeId(type.getId());
        request.setMemberId(reporter.getId());
        request.setTargetId(10);

        given(categoryRepository.findById(category.getId()))
                                .willReturn(Optional.of(category));
        given(typeRepository.findById(type.getId()))
                            .willReturn(Optional.of(type));
        given(memberRepository.findById(reporter.getId()))
                              .willReturn(Optional.of(reporter));
        given(memberRepository.findById(reportedMember.getId()))
                              .willReturn(Optional.of(reportedMember));

        given(reportService.findReportedMemberId(category.getId(), request.getTargetId()))
                           .willReturn(reportedMember.getId());

        given(reportRepository.existsByMemberAndTargetIdAndTypeAndReportedMember(
                any(Member.class),
                anyInt(),
                any(ReportType.class),
                any(Member.class))
        ).willReturn(false);

        int beforeCount = reportedMember.getReportedCount();
        reportServiceImpl.createReport(request);

        int afterCount = reportedMember.getReportedCount();
        Assertions.assertEquals(beforeCount + 1, afterCount);
    }


    MemberRank createMemberRank(String name, int minPoint) {
        MemberRank memberRank = new MemberRank();
        memberRank.setName(RankName.valueOf(name));
        memberRank.setMinPoint(minPoint);
        memberRank.setImageUrl("https://fastly.picsum.photos/id/1001/200/300.jpg");
        return memberRank;
    }

    Member createMember(String email, String password, String nickname, MemberRank memberRank) {
        return new Member(email, password, nickname, memberRank, DateTimeUtils.nowDateTime());
    }

}
