package com.threego.algo.member.command.domain.repository;

import com.threego.algo.member.command.domain.aggregate.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCommandRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
}
