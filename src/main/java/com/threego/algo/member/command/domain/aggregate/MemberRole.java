package com.threego.algo.member.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Member_Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {

    @EmbeddedId
    private MemberRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId") // 복합키 중 memberId 매핑
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId") // 복합키 중 roleId 매핑
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public MemberRole(Member member, Role role) {
        this.member = member;
        this.role = role;
        this.id = new MemberRoleId(member.getId(), role.getId());
    }
}