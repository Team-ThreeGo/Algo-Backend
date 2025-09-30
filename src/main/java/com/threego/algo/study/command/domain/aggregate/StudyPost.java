package com.threego.algo.study.command.domain.aggregate;

import com.threego.algo.common.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Study_Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "study_id", nullable = false)
    private int studyId;

    @Column(name = "member_id", nullable = false)
    private int memberId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;

    @Column(name = "created_at", nullable = false, length = 20)
    private String createdAt;

    @Column(name = "updated_at", length = 20)
    private String updatedAt;

    @Column(nullable = false, length = 1)
    private String visibility = "Y";

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyPostImage> images = new ArrayList<>();

    // 생성자
    public StudyPost(int studyId, int memberId, String title, String content) {
        this.studyId = studyId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.commentCount = 0;
        this.visibility = "Y";
        this.createdAt = DateTimeUtils.nowDateTime();
    }

    // 비즈니스 메소드
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = DateTimeUtils.nowDateTime();
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }

    public void softDelete() {
        this.visibility = "N";
        this.updatedAt = DateTimeUtils.nowDateTime();
    }
}