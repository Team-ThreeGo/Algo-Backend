package com.threego.algo.algorithm.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "algo_roadmap")
@NoArgsConstructor
@Entity
public class AlgoRoadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "`order`", nullable = false)
    private int order;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "question_count")
    private int questionCount;

    public AlgoRoadmap(final String title, final String description, final int order) {
        this.title = title;
        this.description = description;
        this.order = order;
        this.createdAt = DateTimeUtils.nowDateTime();
        this.questionCount = 0;
    }

    public void updateAlgoRoadmap(final String title, final String description, final int order) {
        this.title = title;
        this.description = description;
        this.order = order;
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    public void updateQuestionCount(final int questionCount) {
        if (questionCount < 0) {
            this.questionCount = 0;
        } else {
            this.questionCount = questionCount;
        }
    }
}