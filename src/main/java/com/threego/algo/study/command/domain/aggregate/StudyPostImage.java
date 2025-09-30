package com.threego.algo.study.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Study_Post_Image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyPostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

}