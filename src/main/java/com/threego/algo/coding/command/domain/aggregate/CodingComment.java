package com.threego.algo.coding.command.domain.aggregate;

import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Coding_Comment")
public class CodingComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CodingPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CodingComment  parentId;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(name = "created_at", nullable = false, length = 20)
    private String createdAt;

    @Column(name = "updated_at", length = 20)
    private String updatedAt;

    @Column(nullable = false, length = 1)
    private String visibility = "Y";

}
