package com.threego.algo.studyrecruit.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.RecruitStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Study_Recruit_Post")
@Getter
@Setter
@NoArgsConstructor
public class StudyRecruitPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "expires_at", nullable = false)
    private String expiresAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecruitStatus status;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "comment_count", columnDefinition = "int default 0")
    private int commentCount;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(columnDefinition = "char(1) default 'Y'")
    private String visibility;
//    private VisibilityStatus visibility;

    // 연관관계
    @OneToMany(mappedBy = "studyRecruitPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyRecruitMember> applications = new ArrayList<>();

    @OneToMany(mappedBy = "studyRecruitPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyRecruitComment> comments = new ArrayList<>();

    // 생성자
    public StudyRecruitPost(Member member, String title, String content, String startDate,
                            String endDate, String expiresAt, int capacity, String createdAt) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expiresAt = expiresAt;
        this.capacity = capacity;
        this.status = RecruitStatus.OPEN;
        this.commentCount = 0;
        this.createdAt = createdAt;
    }

    // 비즈니스 메소드

    /* 설명. 모집게시물 소프트 딜리트 */
    public void softDelete() {
        this.visibility = "N";
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    /* 설명. 모집 마감 처리 */
    public void closeRecruitment() {
        this.status = RecruitStatus.CLOSED;
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    /* 설명. 스터디 모집 게시물 댓글 수 카운트 + 1 */
    public void incrementCommentCount() {
        this.commentCount = this.commentCount + 1;
    }

    /* 설명. 스터디 모집 게시물 댓글 수 카운트 - 1 */
    public void decrementCommentCount() {
        this.commentCount = Math.max(0, this.commentCount - 1);
    }


    /* 설명. 모집글 수정 시 업데이트 시간 갱신 */
    public void updatePost(String title, String content, String startDate, String endDate,
                           String expiresAt, int capacity, String visibility) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expiresAt = expiresAt;
        this.capacity = capacity;
        this.visibility = visibility;
        this.updatedAt = DateTimeUtils.nowDateTime();
    }


}