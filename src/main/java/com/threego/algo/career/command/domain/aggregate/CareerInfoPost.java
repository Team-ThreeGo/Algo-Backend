package com.threego.algo.career.command.domain.aggregate;

import com.threego.algo.career.command.domain.aggregate.enums.Status;
import com.threego.algo.member.command.domain.aggregate.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Career_Info_Post")
@Entity
public class CareerInfoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NONE;

    @Column(name = "reject_reason", length = 255)
    private String rejectReason;

    @Column(nullable = false, length = 1)
    private String visibility = "Y";

    @Lob
    private String content;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "comment_count")
    private int commentCount = 0;

    @Column(name = "like_count")
    private int likeCount = 0;

    @Column(name = "created_at", nullable = false, length = 20)
    private String createdAt;
}
