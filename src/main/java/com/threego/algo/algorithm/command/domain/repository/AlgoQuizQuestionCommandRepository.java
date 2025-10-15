package com.threego.algo.algorithm.command.domain.repository;

import com.threego.algo.algorithm.command.domain.aggregate.AlgoPost;
import com.threego.algo.algorithm.command.domain.aggregate.AlgoQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgoQuizQuestionCommandRepository extends JpaRepository<AlgoQuizQuestion, Integer> {
    int countByAlgoPost(final AlgoPost algoPost);

    boolean existsByQuestionLike(final String question);

    boolean existsByAlgoPostIdAndQuestionLike(final int algoPostId, final String question);

    boolean existsByAlgoPostIdAndIdIsNotAndQuestionLike(final int algoPostId, final int id, final String question);
}