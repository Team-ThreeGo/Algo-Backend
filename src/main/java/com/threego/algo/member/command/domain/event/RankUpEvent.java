package com.threego.algo.member.command.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RankUpEvent {

    private final int memberId;
    private final String nickname;
    private final String oldRank;
    private final String newRank;
    private final int currentPoint;

//    public RankUpEvent(int memberId, String nickname, String oldRank, String newRank, int currentPoint) {
//        this.memberId = memberId;
//        this.nickname = nickname;
//        this.oldRank = oldRank;
//        this.newRank = newRank;
//        this.currentPoint = currentPoint;
//    }
}
