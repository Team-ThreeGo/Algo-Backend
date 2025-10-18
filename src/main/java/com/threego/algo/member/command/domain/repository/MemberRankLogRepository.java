package com.threego.algo.member.command.domain.repository;

import com.threego.algo.member.command.domain.aggregate.MemberRankLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRankLogRepository extends JpaRepository<MemberRankLog, Long> {
}
