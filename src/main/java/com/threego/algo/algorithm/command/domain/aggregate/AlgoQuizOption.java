package com.threego.algo.algorithm.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "algo_quiz_option")
@NoArgsConstructor
@Entity
public class AlgoQuizOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "option_text", nullable = false)
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private AlgoQuizQuestion algoQuizQuestion;

    public AlgoQuizOption(final String optionText, final boolean isCorrect, final AlgoQuizQuestion algoQuizQuestion) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.algoQuizQuestion = algoQuizQuestion;
    }

    public void updateAlgoQuizOption(final String optionText, final boolean isCorrect) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }
}