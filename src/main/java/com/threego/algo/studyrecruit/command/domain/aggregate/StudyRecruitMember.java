package com.threego.algo.studyrecruit.command.domain.aggregate;


import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.studyrecruit.command.domain.aggregate.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Study_Recruit_Member")
@Getter
@Setter
@NoArgsConstructor
public class StudyRecruitMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private StudyRecruitPost studyRecruitPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(length = 500, nullable = false)
    private String applicant;


    // 생성자
    public StudyRecruitMember(StudyRecruitPost studyRecruitPost, Member member, String applicant) {
        this.studyRecruitPost = studyRecruitPost;
        this.member = member;
        this.status = ApplicationStatus.PENDING;
        this.applicant = applicant;
    }

    // 비즈니스 메소드
    public void approve() {
        this.status = ApplicationStatus.APPROVED;
    }

    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }


    // 상태 확인 메소드들
    public boolean isPending() {
        return this.status == ApplicationStatus.PENDING;
    }

    public boolean isApproved() {
        return this.status == ApplicationStatus.APPROVED;
    }

    public boolean isRejected() {
        return this.status == ApplicationStatus.REJECTED;
    }

}