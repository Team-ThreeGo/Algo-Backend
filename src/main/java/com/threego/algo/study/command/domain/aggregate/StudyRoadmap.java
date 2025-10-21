package com.threego.algo.study.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Study_Roadmap")
@Getter
@Setter
@NoArgsConstructor
public class StudyRoadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "study_id", nullable = false)
    private int studyId;

    @Column(name = "member_id", nullable = false)
    private int memberId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "`order`")
    private int order;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    // 생성자
    public StudyRoadmap(int studyId, int memberId, String title, String description, int order) {
        this.studyId = studyId;
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.order = order;
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    // 비즈니스 메소드
    public void updateRoadmap(String title, String description, int order) {
        this.title = title;
        this.description = description;
        this.order = order;
    }


}