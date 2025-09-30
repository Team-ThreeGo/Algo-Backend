package com.threego.algo.likes.command.domain.repository;

import com.threego.algo.likes.command.domain.aggregate.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesCommandRepository extends JpaRepository<Likes, Integer> {
}