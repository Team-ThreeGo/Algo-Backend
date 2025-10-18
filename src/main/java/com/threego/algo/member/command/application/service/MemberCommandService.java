package com.threego.algo.member.command.application.service;

import org.springframework.transaction.annotation.Transactional;

public interface MemberCommandService {
    @Transactional
    void updateMemberInfo(int memberId, String newNickname);
}
