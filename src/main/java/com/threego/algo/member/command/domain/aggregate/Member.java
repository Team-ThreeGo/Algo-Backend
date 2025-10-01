package com.threego.algo.member.command.domain.aggregate;

import com.threego.algo.auth.command.application.dto.UserDTO;
import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Table(name = "Member")
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(columnDefinition = "int default 0")
    private int point;

    @Column(name = "reported_count", columnDefinition = "int default 0")
    private int reportedCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberRole> memberRoles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false)
    private MemberRank memberRank;

    public Member(final String email, final String password, final String nickname, final MemberRank memberRank, final String createdAt) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberRank = memberRank;
        this.point = 0;
        this.reportedCount = 0;
        this.status = Status.ACTIVE;
        this.createdAt = createdAt;
    }

    public static Member UserToMember(UserDTO dto, MemberRank defaultRank) {
        return new Member(
            dto.getEmail(),
            dto.getPassword(),
            dto.getNickname(),
            defaultRank,
            DateTimeUtils.nowDateTime()
        );
    }

    public void increaseReportCount() {
        this.reportedCount += 1;
    }

    public void increasePoint(final int point) {
        this.point += point;
    }
}