package com.threego.algo.coding.command.domain.repository;

import com.threego.algo.coding.command.domain.aggregate.CodingPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodingPostImageRepository extends JpaRepository<CodingPostImage, Integer> {
}