package com.threego.algo.member.command.application.service;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.aop.IncreasePoint;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberAttendanceHistory;
import com.threego.algo.member.command.domain.repository.MemberAttendanceHistoryCommandRepository;
import com.threego.algo.member.command.domain.repository.MemberCommandRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberCommandRepository memberRepository;
    private final MemberAttendanceHistoryCommandRepository attendanceRepository;

    public MemberCommandServiceImpl(MemberCommandRepository memberCommandRepository,
                                    MemberAttendanceHistoryCommandRepository attendanceRepository) {
        this.memberRepository = memberCommandRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    @Override
    public void updateMemberInfo(int memberId, String newNickname) {
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        member.setNickname(newNickname);
        memberRepository.save(member);
    }

    @Transactional
    @IncreasePoint(amount = 1)
    public String createAttendance(int memberId) {
        String today = DateTimeUtils.nowDate();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        boolean exists = attendanceRepository.existsByMemberAndAttendAt(member, today);
        if (exists) {
            throw new DataIntegrityViolationException("이미 출석했습니다.");
        }

        MemberAttendanceHistory history = new MemberAttendanceHistory(member, today);
        attendanceRepository.save(history);

        return today;
    }

}
