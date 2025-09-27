package com.threego.algo.algorithm.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "Algo_roadmap")
@NoArgsConstructor
@Entity
public class AlgoRoadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "`order`")
    private int order;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "question_count")
    private int questionCount;

    public AlgoRoadmap(final String title, final String description, final int order, final String createdAt) {
        this.title = title;
        this.description = description;
        this.order = order;
        this.createdAt = createdAt;
        this.questionCount = 0;
    }
}