package com.threego.algo.member.command.domain.aggregate;

import com.threego.algo.member.command.domain.aggregate.enums.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "Role")
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "role")
    private List<MemberRole> memberRoles = new ArrayList<>();
}