package com.threego.algo.coding.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
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
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CodingPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CodingComment parent;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(name = "created_at", nullable = false, length = 20)
    private String createdAt;

    @Column(name = "updated_at", length = 20)
    private String updatedAt;

    @Column(nullable = false, length = 1)
    private String visibility = "Y";

    public CodingComment(Member member, CodingPost post, String content, CodingComment parent) {
        this.member = member;
        this.post = post;
        this.parent = parent;
        this.content = content;
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    public static CodingComment create(Member member, CodingPost post, String content, CodingComment parent) {
        CodingComment comment = new CodingComment();
        comment.member = member;
        comment.post = post;
        comment.parent = parent;
        comment.content = content;
        comment.createdAt = DateTimeUtils.nowDateTime();
        return comment;
    }

    public void update(String content) {
        this.content = content;
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    public void delete() {
        if ("N".equals(this.visibility)) {
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }
        this.visibility = "N";
    }
}
