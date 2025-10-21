package com.threego.algo.algorithm.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "algo_post_image")
@NoArgsConstructor
@Entity
public class AlgoPostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private AlgoPost algoPost;

    public AlgoPostImage(final String imageUrl, final AlgoPost algoPost) {
        this.imageUrl = imageUrl;
        this.algoPost = algoPost;
    }
}