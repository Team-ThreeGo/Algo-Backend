package com.threego.algo.member.command.application.event;

import com.threego.algo.member.command.domain.event.RankUpEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RankUpEventListener {

    @EventListener
    public void handleRankUpEvent(RankUpEvent event) {
        log.info("🎉🐨 [등급업 알림] 회원 {}({})님이 '{}' → '{}' 등급으로 승급했습니다! (현재 포인트: {})",
                event.getMemberId(),
                event.getNickname(),
                event.getOldRank(),
                event.getNewRank(),
                event.getCurrentPoint());
    }
}
