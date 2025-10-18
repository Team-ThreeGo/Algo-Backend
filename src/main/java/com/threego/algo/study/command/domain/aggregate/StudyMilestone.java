package com.threego.algo.study.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Study_Milestone")
@Getter
@Setter
@NoArgsConstructor
public class StudyMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roadmap_id", nullable = false)
    private int roadmapId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "`order`", nullable = false)
    private Integer order;


    // 생성자
    public StudyMilestone(int roadmapId, String title, String description, Integer order) {
        this.roadmapId = roadmapId;
        this.title = title;
        this.description = description;
        this.order = order;

    }

    // 비즈니스 메소드
    public void updateMilestone(String title, String description, Integer order) {
        this.title = title;
        this.description = description;
        this.order = order;
    }

}