package com.talktalkcare.domain.games.repository;

import com.talktalkcare.domain.games.entity.GameCategoryAvgScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameCategoryAvgScoreRepository extends JpaRepository<GameCategoryAvgScore, Long> {
    void deleteByUserIdAndCategoryId(Integer userId, Integer categoryId);
    Optional<GameCategoryAvgScore> findByUserIdAndCategoryId(Integer userId, Integer categoryId);
}