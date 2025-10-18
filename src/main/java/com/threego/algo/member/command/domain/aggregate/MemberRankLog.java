package com.threego.algo.member.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Member_Rank_Log")
public class MemberRankLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int memberId;

    private String oldRank;

    private String newRank;

    private String changedAt;

    public MemberRankLog(int memberId, String oldRank, String newRank, String changedAt) {
        this.memberId = memberId;
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.changedAt = changedAt;
    }
}